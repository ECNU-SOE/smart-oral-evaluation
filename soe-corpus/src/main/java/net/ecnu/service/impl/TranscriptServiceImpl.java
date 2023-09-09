package net.ecnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.ecnu.controller.request.TranscriptReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
//import net.ecnu.interceptor.LoginInterceptor;
import net.ecnu.mapper.CpsgrpMapper;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.model.CpsrcdDO;
import net.ecnu.model.TranscriptDO;
import net.ecnu.mapper.TranscriptMapper;
import net.ecnu.model.common.LoginUser;
import net.ecnu.model.vo.CpsrcdVO;
import net.ecnu.model.vo.TranscriptVO;
import net.ecnu.service.TranscriptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.ecnu.util.IDUtil;
import net.ecnu.util.RequestParamUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        //查询用户的所有题目组的报告列表
        //TODO 支持按照条件参数查询：排序方式、分数区间等
        List<TranscriptDO> transcriptDOS = transcriptMapper.selectList(new QueryWrapper<TranscriptDO>()
                .eq("respondent", currentAccountNo));
        //转换成VO对象返回
        List<TranscriptVO> transcriptVOS = transcriptDOS.stream().map(this::beanProcess).collect(Collectors.toList());
        return transcriptVOS;
    }

    /**
     * TranscriptDO->TranscriptVO
     */
    private TranscriptVO beanProcess(TranscriptDO transcriptDO) {
        TranscriptVO transcriptVO = new TranscriptVO();
        BeanUtils.copyProperties(transcriptDO, transcriptVO);
        return transcriptVO;
    }
}
