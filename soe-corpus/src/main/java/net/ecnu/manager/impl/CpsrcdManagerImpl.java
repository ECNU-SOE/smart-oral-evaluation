package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.controller.request.CpsrcdFilterReq;
import net.ecnu.manager.CpsgrpManager;
import net.ecnu.manager.CpsrcdManager;
import net.ecnu.mapper.CpsgrpMapper;
import net.ecnu.mapper.CpsrcdMapper;
import net.ecnu.mapper.TaggingMapper;
import net.ecnu.model.CorpusDO;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.CpsrcdDO;
import net.ecnu.model.TaggingDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
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
    public IPage<CpsrcdDO> pageByFilter(CpsrcdFilterReq cpsrcdFilter, Page<CpsrcdDO> cpsrcdDOPage,List<String> cpsrcdIds) {
        QueryWrapper<CpsrcdDO> qw = new QueryWrapper<>();
        if (!StringUtils.isEmpty(cpsrcdFilter.getType())) {
            qw.eq("type",cpsrcdFilter.getType());
        }
        //难易程度
        if(!Objects.isNull(cpsrcdFilter.getDifficultyBegin())){
            qw.ge("difficulty",cpsrcdFilter.getDifficultyBegin());
        }
        if(!Objects.isNull(cpsrcdFilter.getDifficultyEnd())){
            qw.le("difficulty",cpsrcdFilter.getDifficultyEnd());
        }
        //文本内容
        if(!StringUtils.isEmpty(cpsrcdFilter.getRefText())){
            qw.like("ref_text",cpsrcdFilter.getRefText());
        }
        qw.in("id",cpsrcdIds);
        qw.orderByDesc("gmt_create");
        return cpsrcdMapper.selectPage(cpsrcdDOPage, qw);
    }

    @Override
    public List<String> getCpsrcdIdsByTagIds(List<Integer> tagIds) {
        if (CollectionUtils.isEmpty(tagIds)){
            List<CpsrcdDO> cpsrcdDOS = cpsrcdMapper.selectList(null);
            return cpsrcdDOS.stream().map(CpsrcdDO::getId).collect(Collectors.toList());
        }
        QueryWrapper<TaggingDO> qw = new QueryWrapper<>();
        qw.eq("entity_type",1);//1:cpsrcd
        qw.and(wq->{
            wq.eq("tag_id",tagIds.get(0));
            for(int i = 1;i<tagIds.size();i++){
                wq.or().eq("tag_id",tagIds.get(i));
            }
        });
        List<TaggingDO> taggingDOS = taggingMapper.selectList(qw);
        return taggingDOS.stream().map(TaggingDO::getEntityId).collect(Collectors.toList());

    }


}
