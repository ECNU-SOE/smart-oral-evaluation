package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.controller.request.CpsrcdFilterReq;
import net.ecnu.manager.CpsrcdManager;
import net.ecnu.mapper.CpsrcdMapper;
import net.ecnu.mapper.TaggingMapper;
import net.ecnu.model.CpsrcdDO;
import net.ecnu.model.TaggingDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CpsrcdManagerImpl implements CpsrcdManager {

    @Autowired
    private CpsrcdMapper cpsrcdMapper;
    @Autowired
    private TaggingMapper taggingMapper;

    @Override
    public List<CpsrcdDO> listByCpsgrpId(String cpsgrpId) {
        return cpsrcdMapper.selectList(new QueryWrapper<CpsrcdDO>()
                .eq("cpsgrp_id", cpsgrpId)
        );
    }

    @Override
    public int countByCpsgrpId(String cpsgrpId) {
        return cpsrcdMapper.selectCount(new QueryWrapper<CpsrcdDO>()
                .eq("cpsgrp_id", cpsgrpId));
    }

    @Override
    public IPage<CpsrcdDO> pageByFilter(CpsrcdFilterReq cpsrcdFilter, Page<CpsrcdDO> cpsrcdDOPage) {
        QueryWrapper<CpsrcdDO> qw = new QueryWrapper<>();
        //语料类型
        if (!StringUtils.isEmpty(cpsrcdFilter.getType())) {
            qw.eq("type", cpsrcdFilter.getType());
        }
        //难易程度
        if (!Objects.isNull(cpsrcdFilter.getDifficultyBegin()) || !Objects.isNull(cpsrcdFilter.getDifficultyEnd())) {
            qw.ge("difficulty", cpsrcdFilter.getDifficultyBegin() != null ? cpsrcdFilter.getDifficultyBegin() : 0)
                    .le("difficulty", cpsrcdFilter.getDifficultyEnd() != null ? cpsrcdFilter.getDifficultyEnd() : 12);
        }
        //文本内容
        if (!StringUtils.isEmpty(cpsrcdFilter.getRefText())) {
            qw.like("ref_text", cpsrcdFilter.getRefText());
        }
        //cpsrcdIds不为空，说名tagIds过滤出了cpsrcd从其中查
        if (!CollectionUtils.isEmpty(cpsrcdFilter.getCpsrcdIds())) {
            qw.in("id", cpsrcdFilter.getCpsrcdIds());
        }
        qw.orderByDesc("gmt_create"); //TODO 设置排序方式
        return cpsrcdMapper.selectPage(cpsrcdDOPage, qw);
    }

    @Override
    public List<CpsrcdDO> getCpsrcdDOs(CpsrcdFilterReq cpsrcdFilter) {
        QueryWrapper<CpsrcdDO> qw = new QueryWrapper<>();
        //语料类型
        if (!StringUtils.isEmpty(cpsrcdFilter.getType())) {
            qw.eq("type", cpsrcdFilter.getType());
        }
        //难易程度
        if (!Objects.isNull(cpsrcdFilter.getDifficultyBegin()) || !Objects.isNull(cpsrcdFilter.getDifficultyEnd())) {
            qw.ge("difficulty", cpsrcdFilter.getDifficultyBegin() != null ? cpsrcdFilter.getDifficultyBegin() : 0)
                    .le("difficulty", cpsrcdFilter.getDifficultyEnd() != null ? cpsrcdFilter.getDifficultyEnd() : 12);
        }
        //文本内容
        if (!StringUtils.isEmpty(cpsrcdFilter.getRefText())) {
            qw.like("ref_text", cpsrcdFilter.getRefText());
        }
        //cpsrcdIds不为空，说名tagIds过滤出了cpsrcd从其中查
            qw.in("id", cpsrcdFilter.getCpsrcdIds());
        qw.orderByDesc("gmt_create");
        return cpsrcdMapper.selectList(qw);
    }

    @Override
    public List<String> getCpsrcdIdsByTagIds(List<Integer> tagIds) {
        // 构建查询条件
        QueryWrapper<TaggingDO> qw = new QueryWrapper<>();
        qw.select("entity_id").in("tag_id",tagIds).groupBy("entity_id")
                .having("count(distinct tag_id) = {0}",tagIds.size());
        // 查询符合条件的cpsrcdIds
        List<TaggingDO> taggingDOS = taggingMapper.selectList(qw);
        return taggingDOS.stream().map(TaggingDO::getEntityId).collect(Collectors.toList());
    }
}
