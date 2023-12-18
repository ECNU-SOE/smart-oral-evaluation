package net.ecnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.controller.request.TranscriptReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
//import net.ecnu.interceptor.LoginInterceptor;
import net.ecnu.mapper.CpsgrpMapper;
import net.ecnu.model.*;
import net.ecnu.mapper.TranscriptMapper;
import net.ecnu.model.common.LoginUser;
import net.ecnu.model.common.PageData;
import net.ecnu.model.dto.req.TranscriptInfoReq;
import net.ecnu.model.dto.resp.TranscriptInfoResp;
import net.ecnu.model.vo.CpsrcdVO;
import net.ecnu.model.vo.TranscriptVO;
import net.ecnu.service.TranscriptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.ecnu.util.IDUtil;
import net.ecnu.util.RequestParamUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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


    public long countByExample(TranscriptDOExample example) {
        return transcriptMapper.countByExample(example);
    }


    public int deleteByExample(TranscriptDOExample example) {
        return transcriptMapper.deleteByExample(example);
    }


    public int deleteByPrimaryKey(String id) {
        return transcriptMapper.deleteByPrimaryKey(id);
    }


    public int insert(TranscriptDO record) {
        return transcriptMapper.insert(record);
    }


    public int insertSelective(TranscriptDO record) {
        return transcriptMapper.insertSelective(record);
    }


    public List<TranscriptDO> selectByExample(TranscriptDOExample example) {
        return transcriptMapper.selectByExample(example);
    }


    public TranscriptDO selectByPrimaryKey(String id) {
        return transcriptMapper.selectByPrimaryKey(id);
    }


    public int updateByExampleSelective(TranscriptDO record,TranscriptDOExample example) {
        return transcriptMapper.updateByExampleSelective(record,example);
    }


    public int updateByExample(TranscriptDO record,TranscriptDOExample example) {
        return transcriptMapper.updateByExample(record,example);
    }


    public int updateByPrimaryKeySelective(TranscriptDO record) {
        return transcriptMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(TranscriptDO record) {
        return transcriptMapper.updateByPrimaryKey(record);
    }


    public int updateBatch(List<TranscriptDO> list) {
        return transcriptMapper.updateBatch(list);
    }


    public int batchInsert(List<TranscriptDO> list) {
        return transcriptMapper.batchInsert(list);
    }

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
        transcriptDO.setSystemScore(BigDecimal.valueOf(transcriptReq.getSuggestedScore()));
        transcriptDO.setResJson(transcriptReq.getResJson());
        //transcript表新增记录
        int insert = transcriptMapper.insert(transcriptDO);
        return transcriptMapper.selectById(transcriptDO.getId());
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
        transcriptVOS.forEach(transcriptVO -> {
            CpsgrpDO cpsgrpDO = cpsgrpMapper.selectById(transcriptVO.getCpsgrpId());
            if (cpsgrpDO != null && cpsgrpDO.getTitle() != null){ //对应的语料组存在 && 名称不为null
                transcriptVO.setCpsgrpName(cpsgrpDO.getTitle());
            }
        });
        return transcriptVOS;
    }

    @Override
    public PageData getTranscriptInfo(TranscriptInfoReq transcriptInfoReq) {
        Page<TranscriptInfoResp> page = new Page<>(transcriptInfoReq.getPageNumber(),transcriptInfoReq.getPageSize());
        IPage<TranscriptInfoResp> pageInfo = transcriptMapper.getTranscriptInfo(transcriptInfoReq,page);
        for (TranscriptInfoResp record : pageInfo.getRecords()) {
            /**获取分页后的批阅人姓名**/
            if(!StringUtils.isEmpty(record.getReviewerCode())){
                String reviewerName = transcriptMapper.getReviewerName(record.getReviewerCode());
                record.setReviewerName(reviewerName);
            }
        }
        PageData pageData = new PageData();
        BeanUtils.copyProperties(pageInfo, pageData);
        return pageData;
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
