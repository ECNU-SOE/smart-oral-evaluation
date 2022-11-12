package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.manager.CpsgrpManager;
import net.ecnu.manager.CpsrcdManager;
import net.ecnu.mapper.CpsgrpMapper;
import net.ecnu.mapper.CpsrcdMapper;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.CpsrcdDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CpsrcdManagerImpl implements CpsrcdManager {

    @Autowired
    private CpsrcdMapper cpsrcdMapper;

    @Override
    public List<CpsrcdDO> listByCpsgrpId(String cpsgrpId) {
        return cpsrcdMapper.selectList(new QueryWrapper<CpsrcdDO>()
                .eq("cpsgrp_id", cpsgrpId)
                .orderByAsc("type")
                .orderByAsc("'order'")
        );
    }

}
