package net.ecnu.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import net.ecnu.controller.request.CorpusFilterReq;
import net.ecnu.manager.CorpusManager;
import net.ecnu.mapper.CorpusMapper;
import net.ecnu.model.CorpusDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CorpusManagerImpl implements CorpusManager {

    @Autowired
    private CorpusMapper corpusMapper;

    @Override
    public IPage<CorpusDO> pageByFilter(CorpusFilterReq corpusFilter, Page<CorpusDO> corpusDOPage) {
        return corpusMapper.selectPage(corpusDOPage, new QueryWrapper<CorpusDO>()
                .eq("del", 0));
    }

    @Override
    public int add(CorpusDO corpusDO) {
        int rows = corpusMapper.insert(corpusDO);
        return rows;
    }
}
