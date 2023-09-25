package net.ecnu.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CorpusFilterReq;
import net.ecnu.controller.request.CpsrcdFilterReq;
import net.ecnu.controller.request.CpsrcdReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.manager.CpsrcdManager;
import net.ecnu.mapper.*;
import net.ecnu.model.*;
import net.ecnu.model.common.PageData;
import net.ecnu.model.vo.CpsrcdVO;
import net.ecnu.service.CpsrcdService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.ecnu.service.OssService;
import net.ecnu.util.IDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * corpus快照 服务实现类
 * </p>
 *
 * @author LYW
 * @since 2023-03-17
 */
@Service
public class CpsrcdServiceImpl extends ServiceImpl<CpsrcdMapper, CpsrcdDO> implements CpsrcdService {

    @Autowired
    private CpsrcdManager cpsrcdManager;

    @Autowired
    private CpsrcdMapper cpsrcdMapper;

    @Autowired
    private CpsgrpMapper cpsgrpMapper;

    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private TaggingMapper taggingMapper;

    @Autowired
    private OssService ossService;

    @Override
    public Object add(CpsrcdReq cpsrcdReq) {
        //数据合法性校验
        CpsgrpDO cpsgrpDO = cpsgrpMapper.selectById(cpsrcdReq.getCpsgrpId());
        TopicDO topicDO = topicMapper.selectById(cpsrcdReq.getTopicId());
        if (cpsgrpDO == null || topicDO == null) throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        //向cpsrcd表中插入新记录
        CpsrcdDO cpsrcdDO = new CpsrcdDO();
        BeanUtils.copyProperties(cpsrcdReq, cpsrcdDO);
        cpsrcdDO.setTags(JSONUtil.toJsonStr(cpsrcdReq.getTags()));
        cpsrcdDO.setId(IDUtil.nextCpsrcdId());
        int insert = cpsrcdMapper.insert(cpsrcdDO);
        cpsrcdDO = cpsrcdMapper.selectById(cpsrcdDO.getId());
        return cpsrcdDO;
    }

    @Override
    public Object pageByFilter(CpsrcdFilterReq cpsrcdFilter, Page<CpsrcdDO> cpsrcdDOPage) {
        IPage<CpsrcdDO> cpsrcdDOIPage = cpsrcdManager.pageByFilter(cpsrcdFilter, cpsrcdDOPage);
        PageData pageData = new PageData();
        BeanUtils.copyProperties(cpsrcdDOIPage, pageData);
        return pageData;
    }

    @Override
    public Object mod(CpsrcdReq cpsrcdReq) {
        CpsrcdDO cpsrcdDO = cpsrcdMapper.selectById(cpsrcdReq.getId());
        if (cpsrcdDO == null) throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        //全量更新 但不会更新null值
        BeanUtils.copyProperties(cpsrcdReq, cpsrcdDO, "cpsgrpId");
        cpsrcdDO.setTags(JSONUtil.toJsonStr(cpsrcdReq.getTags()));
        //同时更新标签映射关系，全量更新，以前的全删，新来的全加
        int deletedNum = taggingMapper.delete(new QueryWrapper<TaggingDO>()
                .eq("entity_id", cpsrcdReq.getId())
        );
        int tagNum = cpsrcdReq.getTags().size();
        for (int i = 0;i<tagNum;i++){
            TagDO tagDO = tagMapper.selectOne(new QueryWrapper<TagDO>()
                    .eq("name", cpsrcdReq.getTags().get(i))
            );
            TaggingDO taggingDO = new TaggingDO();
            taggingDO.setTagId(tagDO.getId());
            taggingDO.setEntityId(cpsrcdReq.getId());
            taggingDO.setEntityType(1);
            taggingMapper.insert(taggingDO);
            //映射关系改变后，需修改标签权重
            Integer total = taggingMapper.selectCount(null);
            Integer count = taggingMapper.selectCount(new QueryWrapper<TaggingDO>()
                    .eq("tag_id", tagDO.getId())
            );
            TagDO newTagDO = new TagDO();
            BeanUtils.copyProperties(tagDO,newTagDO,"weight");
            double w = count / (total*1.00);
            DecimalFormat format = new DecimalFormat("#.00");
            String str = format.format(w);
            double weight = Double.parseDouble(str);
            newTagDO.setWeight(weight);
            int updatenum = tagMapper.updateById(newTagDO);
        }

        cpsrcdDO.setGmtModified(null);//Mysql会自动更新时间
        int i = cpsrcdMapper.updateById(cpsrcdDO);
        cpsrcdDO = cpsrcdMapper.selectById(cpsrcdReq.getId());
//        List<String> list = JSONUtil.toList(cpsrcdDO.getTags(), String.class);

        return cpsrcdDO;
    }

    @Override
    public Object del(String cpsrcdId) {
        CpsrcdDO cpsrcdDO = cpsrcdMapper.selectById(cpsrcdId);
        if (cpsrcdDO == null) throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        int i = cpsrcdMapper.deleteById(cpsrcdId);
        return i;
    }

    @Override
    public CpsrcdVO getCpsrcdDetail(String cpsrcdId) {
        CpsrcdVO cpsrcdVO = new CpsrcdVO();
        CpsrcdDO cpsrcdDO = cpsrcdMapper.selectById(cpsrcdId);
        if (Objects.isNull(cpsrcdDO)) {
            throw new BizException(BizCodeEnum.CPSRCD_NOT_EXIST);
        }
        BeanUtils.copyProperties(cpsrcdDO,cpsrcdVO);
        List<TaggingDO> taggingDOS = taggingMapper.selectList(new QueryWrapper<TaggingDO>()
                .eq("entity_id", cpsrcdId)
        );
        if (taggingDOS.size()!=0){
            List<Integer> tagIds = taggingDOS.stream().map(TaggingDO::getTagId).collect(Collectors.toList());
            List<TagDO> tagDOS = tagMapper.selectBatchIds(tagIds);
            List<String> tagNames = tagDOS.stream().map(TagDO::getName).collect(Collectors.toList());
            cpsrcdVO.setTags(tagNames);
        }

        return cpsrcdVO;
    }


}
