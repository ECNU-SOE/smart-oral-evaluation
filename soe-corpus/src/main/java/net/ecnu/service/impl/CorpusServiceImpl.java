package net.ecnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.ecnu.controller.request.CorpusFilterReq;
import net.ecnu.controller.request.CorpusReq;
import net.ecnu.controller.request.CpsrcdFilterReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.manager.CorpusManager;
import net.ecnu.manager.CpsrcdManager;
import net.ecnu.mapper.CorpusMapper;
import net.ecnu.mapper.CpsrcdMapper;
import net.ecnu.mapper.TagMapper;
import net.ecnu.mapper.TaggingMapper;
import net.ecnu.model.*;
import net.ecnu.model.common.PageData;
import net.ecnu.model.dto.CpsrcdDTO;
import net.ecnu.model.vo.CpsrcdVO;
import net.ecnu.service.CorpusService;
import net.ecnu.util.IDUtil;
import net.ecnu.util.RequestParamUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

//import net.ecnu.interceptor.LoginInterceptor;

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
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private TaggingMapper taggingMapper;
    @Autowired
    private CorpusMapper corpusMapper;
    @Autowired
    private CpsrcdMapper cpsrcdMapper;
    @Autowired
    private CpsrcdManager cpsrcdManager;


    @Override
    public Object pageByFilter(CorpusFilterReq corpusFilter, Page<CorpusDO> corpusDOPage) {
        IPage<CorpusDO> corpusDOIPage = corpusManager.pageByFilter(corpusFilter, corpusDOPage);
        PageData pageData = new PageData();
        BeanUtils.copyProperties(corpusDOIPage, pageData);
        return pageData;
    }

    @Override
    public void add(CorpusReq corpusReq) {
        //获取登录用户信息
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        //生成语料DO对象并插入数据库
        CorpusDO corpusDO = new CorpusDO();
        BeanUtils.copyProperties(corpusReq, corpusDO);
        corpusDO.setId("corpus_" + IDUtil.getSnowflakeId());
        //corpusDO.setCreator(String.valueOf(loginUser.getAccountNo()));
        corpusDO.setCreator(String.valueOf(currentAccountNo));
        corpusManager.add(corpusDO);
    }

    @Override
    public void delCorpusInfo(String corpusId) {
        //逻辑删除
        corpusManager.delCorpusInfo(corpusId);
    }

    @Override
    public void updateCorpusInfo(CorpusReq corpusReq) {
        corpusManager.updateCorpusInfo(corpusReq);
    }
}
