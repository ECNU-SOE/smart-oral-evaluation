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
import net.ecnu.model.vo.CpsrcdVO;
import net.ecnu.service.CpsrcdService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.ecnu.util.IDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
    private TopicMapper topicMapper;

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
        cpsrcdDO.setId(IDUtil.nextCpsrcdId());
        cpsrcdDO.setDel(false);
        int insert = cpsrcdMapper.insert(cpsrcdDO);
        cpsrcdDO = cpsrcdMapper.selectById(cpsrcdDO.getId());
        //tagIds不为空 -> 添加标签
        if (!CollectionUtils.isEmpty(cpsrcdReq.getTagIds())) {
            CpsrcdDO finalCpsrcdDO = cpsrcdDO;
            cpsrcdReq.getTagIds().parallelStream().forEach(tagId -> {
                int taggingRows = taggingMapper.insert(buildTaggingDO(tagId, finalCpsrcdDO.getId()));
            });
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
        PageData pageData = new PageData();
        List<String> cpsrcdIds = cpsrcdManager.getCpsrcdIdsByTagIds(cpsrcdFilter.getTagIds());
        if (CollectionUtils.isEmpty(cpsrcdIds)){
            pageData.setSize(10);
            pageData.setTotal(0);
            pageData.setRecords(new ArrayList<>());
            pageData.setCurrent(1);
            return pageData;
        }
        IPage<CpsrcdDO> cpsrcdDOIPage = cpsrcdManager.pageByFilter(cpsrcdFilter, cpsrcdDOPage,cpsrcdIds);
        List<CpsrcdDO> cpsrcdDOS = cpsrcdDOIPage.getRecords();
        List<CpsrcdDTO> cpsrcdDTOS = cpsrcdDOS.stream().map(this::buildCpsrcdDTOByCpsrcdDO).collect(Collectors.toList());
        BeanUtils.copyProperties(cpsrcdDOIPage, pageData);
        pageData.setRecords(cpsrcdDTOS);
        return pageData;
    }

    @Override
    public Object mod(CpsrcdReq cpsrcdReq) {
        CpsrcdDO cpsrcdDO = cpsrcdMapper.selectById(cpsrcdReq.getId());
        if (cpsrcdDO == null) throw new BizException(BizCodeEnum.CPSRCD_NOT_EXIST);
        //更新cpsrcdDO 全量更新 但不会更新null值
        BeanUtils.copyProperties(cpsrcdReq, cpsrcdDO);
        cpsrcdDO.setGmtModified(null);//Mysql会自动更新时间
        int i = cpsrcdMapper.updateById(cpsrcdDO);

        //同时更新标签映射关系，全量更新，以前的全删，新来的全加
        int deletedNum = taggingMapper.delete(new QueryWrapper<TaggingDO>().eq("entity_id", cpsrcdReq.getId()));
        if (!CollectionUtils.isEmpty(cpsrcdReq.getTagIds())) {
            cpsrcdReq.getTagIds().forEach(tagId -> taggingMapper.insert(buildTaggingDO(tagId, cpsrcdReq.getId())));
        }

        //查询更新结果聚合cpsrcdDTO返回
        cpsrcdDO = cpsrcdMapper.selectById(cpsrcdDO.getId());
        CpsrcdDTO cpsrcdDTO = buildCpsrcdDTOByCpsrcdDO(cpsrcdDO);
        return cpsrcdDTO;
    }

    private CpsrcdDTO buildCpsrcdDTOByCpsrcdDO(CpsrcdDO cpsrcdDO) {
        CpsrcdDTO cpsrcdDTO = new CpsrcdDTO();
        BeanUtils.copyProperties(cpsrcdDO, cpsrcdDTO);
        List<TaggingDO> taggingDOS = taggingMapper.selectList(new QueryWrapper<TaggingDO>().eq("entity_id", cpsrcdDO.getId()));
        if (!CollectionUtils.isEmpty(taggingDOS)) {
            List<TagDO> tagDOs = tagMapper.selectList(new QueryWrapper<TagDO>()
                    .in("id", taggingDOS.stream().map(TaggingDO::getTagId).collect(Collectors.toList())));
            cpsrcdDTO.setTags(tagDOs);
        }
        return cpsrcdDTO;
    }

    @Override
    public Object del(String cpsrcdId) {
        CpsrcdDO cpsrcdDO = cpsrcdMapper.selectById(cpsrcdId);
        if (cpsrcdDO == null) throw new BizException(BizCodeEnum.CPSRCD_NOT_EXIST);
        //删除cpsrcdDO 与对应的 taggingDO 记录
        int delCpsrcds = cpsrcdMapper.deleteById(cpsrcdId);
        int delTaggings = taggingMapper.delete(new QueryWrapper<TaggingDO>().eq("entity_id", cpsrcdId));
        return delCpsrcds + delTaggings;
    }

    @Override
    public Object getCpsrcdDetail(String cpsrcdId) {
        CpsrcdDO cpsrcdDO = cpsrcdMapper.selectById(cpsrcdId);
        if (cpsrcdDO==null)
            throw new BizException(BizCodeEnum.CPSRCD_NOT_EXIST);
        CpsrcdDTO cpsrcdDTO = buildCpsrcdDTOByCpsrcdDO(cpsrcdDO);
        List<TopicCpsDO> count = topicCpsMapper.selectList(new QueryWrapper<TopicCpsDO>()
                .eq("cpsrcd_id", cpsrcdId)
        );
        cpsrcdDTO.setUsedBy(count.size());
        return cpsrcdDTO;
    }

    @Override
    public Object random(CpsrcdFilterReq cpsrcdFilterReq) {
        QueryWrapper<CpsrcdDO> qw = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(cpsrcdFilterReq.getTagIds())){
            List<String> cpsrcdIds = cpsrcdManager.getCpsrcdIdsByTagIds(cpsrcdFilterReq.getTagIds());
            if (CollectionUtils.isEmpty(cpsrcdIds))
                throw new BizException(BizCodeEnum.CPSRCD_NOT_EXIST);
            qw.in("id",cpsrcdIds);
        }
        List<CpsrcdDO> cpsrcdDOS = cpsrcdMapper.selectList(qw);
        if (CollectionUtils.isEmpty(cpsrcdDOS))
            throw new BizException(BizCodeEnum.CPSRCD_NOT_EXIST);
        int index = (int) (Math.random()* cpsrcdDOS.size());
        CpsrcdDO cpsrcdDO = cpsrcdDOS.get(index);
        return buildCpsrcdDTOByCpsrcdDO(cpsrcdDO);
    }


}
