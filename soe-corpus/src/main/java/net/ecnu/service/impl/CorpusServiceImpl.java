package net.ecnu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.CorpusFilterReq;
import net.ecnu.controller.request.CorpusReq;
import net.ecnu.interceptor.LoginInterceptor;
import net.ecnu.manager.CorpusManager;
import net.ecnu.model.CorpusDO;
import net.ecnu.mapper.CorpusMapper;
import net.ecnu.model.common.LoginUser;
import net.ecnu.model.common.PageData;
import net.ecnu.service.CorpusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.ecnu.util.IDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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

    @Override
    public Object add(CorpusReq corpusReq) {
        //获取登录用户信息
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        //生成语料DO对象并插入数据库
        CorpusDO corpusDO = new CorpusDO();
        BeanUtils.copyProperties(corpusReq, corpusDO);
        corpusDO.setId("corpus_" + IDUtil.getSnowflakeId());
        corpusDO.setCreator(String.valueOf(loginUser.getAccountNo()));
        int rows = corpusManager.add(corpusDO);
        return rows;
    }

}
