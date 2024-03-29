package net.ecnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CpsrcdFilterReq;
import net.ecnu.controller.request.CpsrcdReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.manager.CpsrcdManager;
import net.ecnu.mapper.*;
import net.ecnu.model.*;
import net.ecnu.model.common.PageData;
import net.ecnu.model.dto.CpsrcdDTO;
import net.ecnu.service.CpsrcdService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.ecnu.util.IDUtil;
import net.ecnu.util.TimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.xml.crypto.Data;
import java.util.Date;
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
    private TopicCpsMapper topicCpsMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TaggingMapper taggingMapper;

    @Override
    public Object add(CpsrcdReq cpsrcdReq) {
        //向cpsrcd表中插入新记录
        CpsrcdDO cpsrcdDO = new CpsrcdDO();
        BeanUtils.copyProperties(cpsrcdReq, cpsrcdDO);
        String cpsrcdId = IDUtil.nextCpsrcdId();
        cpsrcdDO.setId(cpsrcdId);
        cpsrcdDO.setDel(false);
        int rows = cpsrcdMapper.insert(cpsrcdDO);
        cpsrcdDO = cpsrcdMapper.selectById(cpsrcdDO.getId());
        //tagIds不为空 -> 添加标签
        if (!CollectionUtils.isEmpty(cpsrcdReq.getTagIds())) {
            cpsrcdReq.getTagIds().forEach(tagId -> taggingMapper.insert(buildTaggingDO(tagId, cpsrcdId)));
            rows += cpsrcdReq.getTagIds().size();
        }
        //查询添加结果 返回数据
        CpsrcdDTO cpsrcdDTO = buildCpsrcdDTOByCpsrcdDO(cpsrcdDO);
        return cpsrcdDTO;
    }

    private TaggingDO buildTaggingDO(Integer tagId, String entityId) {
        TaggingDO taggingDO = new TaggingDO();
        taggingDO.setTagId(tagId);
        taggingDO.setEntityId(entityId);
        taggingDO.setEntityType(1);
        return taggingDO;
    }

    @Override
    public Object pageByFilter(CpsrcdFilterReq cpsrcdFilter, Page<CpsrcdDO> cpsrcdDOPage) {
        //tagIds不为空，过滤查询包含tagId的cpsrcd
        if (!CollectionUtils.isEmpty(cpsrcdFilter.getTagIds())) {
            List<String> cpsrcdIds = cpsrcdManager.getCpsrcdIdsByTagIds(cpsrcdFilter.getTagIds());
            cpsrcdFilter.setCpsrcdIds(cpsrcdIds); //设置cpsrcdIds的查询范围
            if (CollectionUtils.isEmpty(cpsrcdIds)) return new PageData(1, 10);
        }
        IPage<CpsrcdDO> cpsrcdDOIPage = cpsrcdManager.pageByFilter(cpsrcdFilter, cpsrcdDOPage);
        List<CpsrcdDO> cpsrcdDOS = cpsrcdDOIPage.getRecords();
        List<CpsrcdDTO> cpsrcdDTOS = cpsrcdDOS.parallelStream().map(this::buildCpsrcdDTOByCpsrcdDO).collect(Collectors.toList());
        //聚合返回数据
        PageData pageData = new PageData();
        BeanUtils.copyProperties(cpsrcdDOIPage, pageData);
        pageData.setRecords(cpsrcdDTOS);
        return pageData;
    }

    @Override
    public Object mod(CpsrcdReq cpsrcdReq) {
        CpsrcdDO cpsrcdDO = cpsrcdMapper.selectById(cpsrcdReq.getId());
        if (cpsrcdDO == null) throw new BizException(BizCodeEnum.CPSRCD_NOT_EXIST);
        //校验如果有语料组正在使用，不允许更新文本refText
        List<TopicCpsDO> topicCpsDOS = topicCpsMapper.selectList(new QueryWrapper<TopicCpsDO>().eq("cpsrcd_id", cpsrcdReq.getId()));
        if (!CollectionUtils.isEmpty(topicCpsDOS) && !Objects.equals(cpsrcdReq.getRefText(), cpsrcdDO.getRefText())) {
            throw new BizException(BizCodeEnum.CPSRCD_IS_USING);
        }
        //更新cpsrcdDO 全量更新 但不会更新null值
        BeanUtils.copyProperties(cpsrcdReq, cpsrcdDO);
        cpsrcdDO.setGmtModified(null);//Mysql会自动更新时间
        int i = cpsrcdMapper.updateById(cpsrcdDO);

        //同时更新标签映射关系，全量更新，以前的全删，新来的全加
        int deletedNum = taggingMapper.delete(new QueryWrapper<TaggingDO>().eq("entity_id", cpsrcdReq.getId()));
        if (!CollectionUtils.isEmpty(cpsrcdReq.getTagIds())) {
            for (int j = 0; j < cpsrcdReq.getTagIds().size(); j++) {
                Integer tagId = cpsrcdReq.getTagIds().get(j);
                int insertNums = taggingMapper.insert(buildTaggingDO(tagId, cpsrcdReq.getId()));
            }
        }

        //查询更新结果聚合cpsrcdDTO返回
        cpsrcdDO = cpsrcdMapper.selectById(cpsrcdDO.getId());
        CpsrcdDTO cpsrcdDTO = buildCpsrcdDTOByCpsrcdDO(cpsrcdDO);
        return cpsrcdDTO;
    }

    private CpsrcdDTO buildCpsrcdDTOByCpsrcdDO(CpsrcdDO cpsrcdDO) {
        CpsrcdDTO cpsrcdDTO = new CpsrcdDTO();
        BeanUtils.copyProperties(cpsrcdDO, cpsrcdDTO);
        //聚合题目标签
        List<TaggingDO> taggingDOS = taggingMapper.selectList(new QueryWrapper<TaggingDO>().eq("entity_id", cpsrcdDO.getId()));
        if (!CollectionUtils.isEmpty(taggingDOS)) {
            List<TagDO> tagDOs = tagMapper.selectList(new QueryWrapper<TagDO>()
                    .in("id", taggingDOS.stream().map(TaggingDO::getTagId).collect(Collectors.toList())));
            cpsrcdDTO.setTags(tagDOs);
        }
        //统计使用频次
        Integer cnt = topicCpsMapper.selectCount(new QueryWrapper<TopicCpsDO>().eq("cpsrcd_id", cpsrcdDO.getId()));
        cpsrcdDTO.setUsageCnt(cnt);
        return cpsrcdDTO;
    }

    @Override
    public Object del(String cpsrcdId) {
        CpsrcdDO cpsrcdDO = cpsrcdMapper.selectById(cpsrcdId);
        if (cpsrcdDO == null) throw new BizException(BizCodeEnum.CPSRCD_NOT_EXIST);
        //校验是否有语料组在使用
        List<TopicCpsDO> topicCpsDOS = topicCpsMapper.selectList(new QueryWrapper<TopicCpsDO>().eq("cpsrcd_id", cpsrcdId));
        if (!CollectionUtils.isEmpty(topicCpsDOS)) {
            throw new BizException(BizCodeEnum.CPSRCD_IS_USING);
        }
        //删除cpsrcdDO 与对应的 taggingDO 记录
        int delCpsrcds = cpsrcdMapper.deleteById(cpsrcdId);
        int delTaggings = taggingMapper.delete(new QueryWrapper<TaggingDO>().eq("entity_id", cpsrcdId));
        return delCpsrcds + delTaggings;
    }

    @Override
    public Object getCpsrcdDetail(String cpsrcdId) {
        CpsrcdDO cpsrcdDO = cpsrcdMapper.selectById(cpsrcdId);
        if (cpsrcdDO == null) throw new BizException(BizCodeEnum.CPSRCD_NOT_EXIST);
        CpsrcdDTO cpsrcdDTO = buildCpsrcdDTOByCpsrcdDO(cpsrcdDO);
        List<TopicCpsDO> count = topicCpsMapper.selectList(new QueryWrapper<TopicCpsDO>()
                .eq("cpsrcd_id", cpsrcdId)
        );
        cpsrcdDTO.setUsageCnt(count.size());
        return cpsrcdDTO;
    }

    @Override
    public Object random(CpsrcdFilterReq cpsrcdFilterReq) {
        if (CollectionUtils.isEmpty(cpsrcdFilterReq.getTagIds())) {
            CpsrcdDO cpsrcdDO = cpsrcdMapper.getRand(cpsrcdFilterReq);
            if (cpsrcdDO==null)
                throw new BizException(BizCodeEnum.CPSRCD_NOT_EXIST);
            return buildCpsrcdDTOByCpsrcdDO(cpsrcdDO);
        }else {
            List<String> cpsrcdIds = cpsrcdManager.getCpsrcdIdsByTagIds(cpsrcdFilterReq.getTagIds());
            if (cpsrcdIds==null||cpsrcdIds.size()==0)
                throw new BizException(BizCodeEnum.CPSRCD_NOT_EXIST);
            cpsrcdFilterReq.setCpsrcdIds(cpsrcdIds);
            Page<CpsrcdDO> cpsrcdDOPage = new Page<>();
            List<CpsrcdDO> cpsrcdDOs = cpsrcdManager.getCpsrcdDOs(cpsrcdFilterReq);
            if (CollectionUtils.isEmpty(cpsrcdDOs))
                throw new BizException(BizCodeEnum.CPSRCD_NOT_EXIST);
            int index = (int) (Math.random() * cpsrcdDOs.size());
            CpsrcdDO cpsrcdDO = cpsrcdDOs.get(index);
            return buildCpsrcdDTOByCpsrcdDO(cpsrcdDO);
        }
    }


}
