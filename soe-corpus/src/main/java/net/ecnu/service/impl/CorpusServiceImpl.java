package net.ecnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CorpusFilterReq;
import net.ecnu.manager.CorpusManager;
import net.ecnu.model.CorpusDO;
import net.ecnu.mapper.CorpusMapper;
import net.ecnu.model.PageData;
import net.ecnu.service.CorpusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 语料原型 服务实现类
 * </p>
 *
 * @author LYW
 * @since 2022-10-20
 */
@Service
public class CorpusServiceImpl extends ServiceImpl<CorpusMapper, CorpusDO> implements CorpusService {

    @Autowired
    private CorpusManager corpusManager;

    @Override
    public Object pageByFilter(CorpusFilterReq corpusFilter, Page<CorpusDO> corpusDOPage) {
        IPage<CorpusDO> corpusDOIPage = corpusManager.pageByFilter(corpusFilter, corpusDOPage);
        PageData pageData = new PageData();
        BeanUtils.copyProperties(corpusDOIPage, pageData);
        return pageData;
    }
}
