package net.ecnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.ecnu.controller.request.TranscriptReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
//import net.ecnu.interceptor.LoginInterceptor;
import net.ecnu.mapper.CpsgrpMapper;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.TranscriptDO;
import net.ecnu.mapper.TranscriptMapper;
import net.ecnu.model.common.LoginUser;
import net.ecnu.service.TranscriptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.ecnu.util.IDUtil;
import net.ecnu.util.RequestParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 成绩单 服务实现类
 * </p>
 *
 * @author LYW
 * @since 2023-03-28
 */
@Service
public class TranscriptServiceImpl extends ServiceImpl<TranscriptMapper, TranscriptDO> implements TranscriptService {

    @Autowired
    private CpsgrpMapper cpsgrpMapper;

    @Autowired
    private TranscriptMapper transcriptMapper;


    @Override
    public Object save(TranscriptReq transcriptReq) {
        //获取登录用户信息
        //LoginUser loginUser = LoginInterceptor.threadLocal.get();
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        //if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN);
        //校验参数
        CpsgrpDO cpsgrpDO = cpsgrpMapper.selectById(transcriptReq.getCpsgrpId());
        if (cpsgrpDO == null) throw new BizException(BizCodeEnum.CPSGRP_NOT_EXIST);
        //生成transcriptDO对象
        TranscriptDO transcriptDO = new TranscriptDO();
        transcriptDO.setId(IDUtil.nextTranscriptId());
        transcriptDO.setCpsgrpId(transcriptReq.getCpsgrpId());
        transcriptDO.setRespondent(currentAccountNo);
        transcriptDO.setSuggestedScore(BigDecimal.valueOf(transcriptReq.getSuggestedScore()));
        transcriptDO.setResJson(transcriptReq.getResJson());
        //transcript表新增记录
        int insert = transcriptMapper.insert(transcriptDO);
        transcriptDO = transcriptMapper.selectById(transcriptDO.getId());
        return transcriptDO;
    }

    @Override
    public Object getTranscript(TranscriptReq transcriptReq) {
        //获取登录用户信息
        //LoginUser loginUser = LoginInterceptor.threadLocal.get();
        //if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN);
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        //校验参数
        CpsgrpDO cpsgrpDO = cpsgrpMapper.selectById(transcriptReq.getCpsgrpId());
        if (cpsgrpDO == null) throw new BizException(BizCodeEnum.CPSGRP_NOT_EXIST);
        //查询用户指定cpsgrpId的题目组的报告列表
        List<TranscriptDO> transcriptDOS = transcriptMapper.selectList(new QueryWrapper<TranscriptDO>()
                .eq("cpsgrp_id", transcriptReq.getCpsgrpId())
                .eq("respondent", currentAccountNo));
        return transcriptDOS;
    }
}
