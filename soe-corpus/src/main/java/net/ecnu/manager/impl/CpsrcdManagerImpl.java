package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.controller.request.CpsrcdFilterReq;
import net.ecnu.manager.CpsgrpManager;
import net.ecnu.manager.CpsrcdManager;
import net.ecnu.mapper.CpsgrpMapper;
import net.ecnu.mapper.CpsrcdMapper;
import net.ecnu.model.CorpusDO;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.CpsrcdDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class CpsrcdManagerImpl implements CpsrcdManager {

    @Autowired
    private CpsrcdMapper cpsrcdMapper;

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
        QueryWrapper<CpsrcdDO> cpsrcdDOQueryWrapper = new QueryWrapper<>();
        //语料id
        if (!StringUtils.isEmpty(cpsrcdFilter.getCpsrcdId())) {
            cpsrcdDOQueryWrapper.eq("id",cpsrcdFilter.getCpsrcdId());
        }
        //测评模式
        if(!Objects.isNull(cpsrcdFilter.getEvalMode())){
            cpsrcdDOQueryWrapper.eq("eval_mode",cpsrcdFilter.getEvalMode());
        }
        if (!StringUtils.isEmpty(cpsrcdFilter.getType())) {
            cpsrcdDOQueryWrapper.eq("type",cpsrcdFilter.getType());
        }
        //难易程度
        if(!Objects.isNull(cpsrcdFilter.getDifficulty())){
            cpsrcdDOQueryWrapper.eq("difficulty",cpsrcdFilter.getDifficulty());
        }
        //文本内容
        if(!StringUtils.isEmpty(cpsrcdFilter.getTextValue())){
            cpsrcdDOQueryWrapper.like("ref_text",cpsrcdFilter.getTextValue());
        }
        return cpsrcdMapper.selectPage(cpsrcdDOPage, cpsrcdDOQueryWrapper);
    }

}
