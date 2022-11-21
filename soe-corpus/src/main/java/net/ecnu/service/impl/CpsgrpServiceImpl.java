package net.ecnu.service.impl;

import net.ecnu.controller.request.CpsgrpCreateReq;
import net.ecnu.controller.request.CpsgrpFilterReq;
import net.ecnu.controller.request.TranscriptReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.interceptor.LoginInterceptor;
import net.ecnu.manager.CpsgrpManager;
import net.ecnu.manager.CpsrcdManager;
import net.ecnu.mapper.CorpusMapper;
import net.ecnu.mapper.CpsrcdMapper;
import net.ecnu.mapper.TranscriptMapper;
import net.ecnu.model.CorpusDO;
import net.ecnu.model.CpsgrpDO;
import net.ecnu.mapper.CpsgrpMapper;
import net.ecnu.model.CpsrcdDO;
import net.ecnu.model.TranscriptDO;
import net.ecnu.model.common.LoginUser;
import net.ecnu.model.common.PageData;
import net.ecnu.model.dto.ScoreDTO;
import net.ecnu.model.vo.CpsgrpDetailVO;
import net.ecnu.model.vo.CpsgrpVO;
import net.ecnu.model.vo.CpsrcdVO;
import net.ecnu.service.CpsgrpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.ecnu.util.IDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * <p>
 * 语料组(作业、试卷、测验) 服务实现类
 * </p>
 *
 * @author LYW
 * @since 2022-11-02
 */
@Service
public class CpsgrpServiceImpl extends ServiceImpl<CpsgrpMapper, CpsgrpDO> implements CpsgrpService {

    @Autowired
    private CpsrcdManager cpsrcdManager;

    @Autowired
    private CpsgrpManager cpsgrpManager;

    @Autowired
    private CpsgrpMapper cpsgrpMapper;

    @Autowired
    private CorpusMapper corpusMapper;

    @Autowired
    private CpsrcdMapper cpsrcdMapper;

    @Autowired
    private TranscriptMapper transcriptMapper;


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Object create(CpsgrpCreateReq cpsgrpCreateReq) {
        //获取登录用户信息
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        //处理生成 cpsgrpDO 对象，并插入数据库
        CpsgrpDO cpsgrpDO = new CpsgrpDO();
        BeanUtils.copyProperties(cpsgrpCreateReq, cpsgrpDO);
        cpsgrpDO.setId("cpsgrp_" + IDUtil.getSnowflakeId());
        cpsgrpDO.setCreator(loginUser.getAccountNo());
        int rows = cpsgrpMapper.insert(cpsgrpDO);
        //处理生成 cpsrcdDO 对象, 并插入数据库
        List<CorpusDO> corpusDOS = corpusMapper.selectBatchIds(cpsgrpCreateReq.getCorpusIds());
        List<CpsrcdDO> cpsrcdDOS = corpusDOS.stream().map(this::beanProcess).collect(Collectors.toList());
        for (int i = 0; i < cpsrcdDOS.size(); i++) {
            cpsrcdDOS.get(i).setId("cpsrcd_" + IDUtil.getSnowflakeId());
            cpsrcdDOS.get(i).setCpsgrpId(cpsgrpDO.getId());
            cpsrcdDOS.get(i).setOrder(i + 1);
            cpsrcdMapper.insert(cpsrcdDOS.get(i));
        }
        rows += cpsrcdDOS.size();
        return cpsgrpDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Object del(String cpsgrpId) {
        //获取登录用户信息,判断操作是否越权
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        CpsgrpDO cpsgrpDO = cpsgrpMapper.selectById(cpsgrpId);
        if (cpsgrpDO == null || !loginUser.getAccountNo().equals(cpsgrpDO.getCreator())) {
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        }
        //获取cpsrcdIds
        List<CpsrcdDO> cpsrcdDOS = cpsrcdManager.listByCpsgrpId(cpsgrpId);
        List<String> cpsrcdIds = cpsrcdDOS.stream().map(CpsrcdDO::getId).collect(Collectors.toList());
        //删除 cpsgrp 与 对应的cpsrcd
        int rows = cpsrcdMapper.deleteBatchIds(cpsrcdIds);
        rows += cpsgrpMapper.deleteById(cpsgrpId);
        return rows;
    }

    @Override
    public Object detail(String cpsgrpId) {
        CpsgrpDO cpsgrpDO = cpsgrpMapper.selectById(cpsgrpId);
        if (cpsgrpDO == null) throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        //处理cpsgrpVO题目组对象
        CpsgrpDetailVO cpsgrpDetailVO = new CpsgrpDetailVO();
        BeanUtils.copyProperties(cpsgrpDO, cpsgrpDetailVO);
        //处理cpsrcdVOS题目列表
        List<CpsrcdDO> cpsrcdDOS = cpsrcdManager.listByCpsgrpId(cpsgrpId);
        List<CpsrcdVO> cpsrcdVOS = cpsrcdDOS.stream().map(this::beanProcess).collect(Collectors.toList());
        cpsgrpDetailVO.setCpsrcdList(cpsrcdVOS);
        return cpsgrpDetailVO;
    }


    @Override
    public Object genTranscript(TranscriptReq transcriptReq) {
        //获取登录用户信息
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN);
        //处理生成TranscriptDO对象
        TranscriptDO transcriptDO = new TranscriptDO();
        transcriptDO.setId("transcript_" + IDUtil.getSnowflakeId());
        transcriptDO.setCpsgrpId(transcriptReq.getCpsgrpId());
        transcriptDO.setRespondent(loginUser.getAccountNo());
        //计算报告完整度得分
        Double pronCompletion = computePronCompletion(transcriptReq.getScores());
        transcriptDO.setPronCompletion(BigDecimal.valueOf(pronCompletion));
        //计算报告准确度得分
        Double pronAccuracy = computePronAccuracy(transcriptReq.getScores());
        transcriptDO.setPronAccuracy(BigDecimal.valueOf(pronAccuracy));
        //计算报告流畅性得分
        Double pronFluency = computePronFluency(transcriptReq.getScores());
        transcriptDO.setPronFluency(BigDecimal.valueOf(pronFluency));
        //计算建议报告得分
        Double suggestedScore = computeSuggestedScore(transcriptReq.getScores());
        transcriptDO.setSuggestedScore(BigDecimal.valueOf(suggestedScore));
        //插入数据库
        transcriptMapper.insert(transcriptDO);
        return transcriptMapper.selectById(transcriptDO.getId());
    }

    @Override
    public Object pageByFilter(CpsgrpFilterReq cpsgrpFilter, PageData pageData) {
        //获取分页后的cpsgrpDOS
        List<CpsgrpDO> cpsgrpDOS = cpsgrpManager.listByFilter(cpsgrpFilter, pageData);
        //统计过滤后的记录总数
        int totalCount = cpsgrpManager.countByFilter(cpsgrpFilter);
        pageData.setTotal(totalCount);
        //生成处理cpsgrpVOS对象
        List<CpsgrpVO> cpsgrpVOS = cpsgrpDOS.stream().map(cpsgrpDO -> {
            CpsgrpVO cpsgrpVO = beanProcess(cpsgrpDO);
            //统计题目数量
            int cpsrcdNum = cpsrcdManager.countByCpsgrpId(cpsgrpVO.getId());
            cpsgrpVO.setCpsrcdNum(cpsrcdNum);
            return cpsgrpVO;
        }).collect(Collectors.toList());
        pageData.setRecords(cpsgrpVOS);
        //返回分页对象
        return pageData;
    }

    /**
     * 计算报告建议得分
     */
    private Double computeSuggestedScore(List<ScoreDTO> scores) {
        int totalWords = scores.stream().mapToInt(ScoreDTO::getTotalWords).sum();
        int wrongWords = scores.stream().mapToInt(ScoreDTO::getWrongWords).sum();
        Double suggestedScore = (1 - wrongWords * 1.0 / totalWords) * 100;
        return suggestedScore;
    }

    /**
     * 计算报告的pronCompletion评分
     */
    private Double computePronCompletion(List<ScoreDTO> scores) {
        OptionalDouble averageOptional = scores.stream().mapToDouble(ScoreDTO::getPronCompletion).average();
        Double average = averageOptional.isPresent() ? averageOptional.getAsDouble() : null;
        if (average != null) average = average * 10;
        return average;
    }

    /**
     * 计算报告的pronAccuracy评分
     */
    private Double computePronAccuracy(List<ScoreDTO> scores) {
        OptionalDouble averageOptional = scores.stream().mapToDouble(ScoreDTO::getPronAccuracy).average();
        Double average = averageOptional.isPresent() ? averageOptional.getAsDouble() : null;
        if (average != null) average = average / 10;
        return average;
    }

    /**
     * 计算报告的pronFluency评分
     */
    private Double computePronFluency(List<ScoreDTO> scores) {
        OptionalDouble averageOptional = scores.stream().mapToDouble(ScoreDTO::getPronFluency).average();
        Double average = averageOptional.isPresent() ? averageOptional.getAsDouble() : null;
        if (average != null) average = average * 10;
        return average;
    }

    /**
     * CorpusDO->CpsrcdDO
     */
    private CpsrcdDO beanProcess(CorpusDO corpusDO) {
        CpsrcdDO cpsgrpDO = new CpsrcdDO();
        BeanUtils.copyProperties(corpusDO, cpsgrpDO);
        return cpsgrpDO;
    }

    /**
     * CpsrcdDO->CpsrcdVO
     */
    private CpsrcdVO beanProcess(CpsrcdDO cpsgrpDO) {
        CpsrcdVO cpsrcdVO = new CpsrcdVO();
        BeanUtils.copyProperties(cpsgrpDO, cpsrcdVO);
        return cpsrcdVO;
    }

    /**
     * CpsgrpDO->CpsgrpVO
     */
    private CpsgrpVO beanProcess(CpsgrpDO cpsgrpDO) {
        CpsgrpVO cpsgrpVO = new CpsgrpVO();
        BeanUtils.copyProperties(cpsgrpDO, cpsgrpVO);
        return cpsgrpVO;
    }
}
