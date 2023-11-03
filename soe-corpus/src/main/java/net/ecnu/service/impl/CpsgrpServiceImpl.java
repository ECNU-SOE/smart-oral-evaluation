package net.ecnu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.ecnu.controller.request.CpsgrpReq;
import net.ecnu.controller.request.CpsgrpFilterReq;
import net.ecnu.controller.request.TranscriptReq;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
//import net.ecnu.interceptor.LoginInterceptor;
import net.ecnu.feign.UserFeignService;
import net.ecnu.manager.CpsgrpManager;
import net.ecnu.manager.CpsrcdManager;
import net.ecnu.manager.TopicManager;
import net.ecnu.mapper.*;
import net.ecnu.model.*;
import net.ecnu.model.common.PageData;
import net.ecnu.model.dto.ScoreDTO;
import net.ecnu.model.vo.CpsgrpVO;
import net.ecnu.model.vo.CpsrcdVO;
import net.ecnu.model.vo.TopicVO;
import net.ecnu.service.CpsgrpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.ecnu.util.IDUtil;
import net.ecnu.util.JsonData;
import net.ecnu.util.RequestParamUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;
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
    private CpsrcdMapper cpsrcdMapper;

    @Autowired
    private CpsgrpManager cpsgrpManager;

    @Autowired
    private TopicManager topicManager;

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private CpsgrpMapper cpsgrpMapper;

    @Autowired
    private TaggingMapper taggingMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ClassCpsgrpMapper classCpsgrpMapper;

    @Autowired
    private TopicCpsMapper topicCpsMapper;


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Object create(CpsgrpReq cpsgrpReq) {
        //获取当前用户信息
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if (currentAccountNo == null) throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN);
        //处理生成 cpsgrpDO 对象，并插入数据库
        CpsgrpDO cpsgrpDO = new CpsgrpDO();
        BeanUtils.copyProperties(cpsgrpReq, cpsgrpDO, "id");
        cpsgrpDO.setId(IDUtil.nextCpsgrpId());
        cpsgrpDO.setCreator(currentAccountNo);
        int rows = cpsgrpMapper.insert(cpsgrpDO);
        //查询数据库返回创建的对象
        cpsgrpDO = cpsgrpMapper.selectById(cpsgrpDO.getId());
        return cpsgrpDO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Object del(String cpsgrpId) {
        //判断操作cpsgrp是否存在
        CpsgrpDO cpsgrpDO = cpsgrpMapper.selectById(cpsgrpId);
        if (cpsgrpDO == null) {
            throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        }
        //TODO 其他用户的 误删问题
        cpsgrpDO.setDel(1);
        int i = cpsgrpMapper.updateById(cpsgrpDO);
        return "del success";
    }

    @Override
    public Object detail(String cpsgrpId) {
        CpsgrpDO cpsgrpDO = cpsgrpMapper.selectById(cpsgrpId);
        if (cpsgrpDO == null) throw new BizException(BizCodeEnum.UNAUTHORIZED_OPERATION);
        //生成cpsgrpVO语料组对象
        CpsgrpVO cpsgrpVO = new CpsgrpVO();
        BeanUtils.copyProperties(cpsgrpDO, cpsgrpVO);
        //获取topic大题列表
        List<TopicDO> topicDOS = topicManager.listByCpsgrpId(cpsgrpDO.getId());
        if (CollectionUtils.isEmpty(topicDOS)) return cpsgrpVO;    //没有大题，返回cpsgrp
        List<TopicVO> topicVOS = topicDOS.stream().map(this::beanProcess).collect(Collectors.toList());
        cpsgrpVO.setTopics(topicVOS);
        //获取cpsrcd子题列表
        List<TopicCpsDO> topicCpsDOS = topicCpsMapper.selectList(new QueryWrapper<TopicCpsDO>()
                .in("topic_id", topicDOS.parallelStream().map(TopicDO::getId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(topicCpsDOS)) return cpsgrpVO;    //没有子题，返回cpsgrp
        List<CpsrcdVO> cpsrcdVOS = topicCpsDOS.parallelStream().map(this::beanProcess).filter(Objects::nonNull)
                .collect(Collectors.toList());
        //聚合cpsrcd子题到topic大题中
        topicVOS.forEach(topicVO -> {
            List<CpsrcdVO> subCpsrcdVOs = cpsrcdVOS.stream().filter(cpsrcdVO -> cpsrcdVO.getTopicId()
                    .equals(topicVO.getId())).collect(Collectors.toList());
            topicVO.setSubCpsrcds(subCpsrcdVOs);
        });
        return cpsgrpVO;
    }


    @Override
    public Object pageByFilter(CpsgrpFilterReq cpsgrpFilter, PageData pageData) {
        //查询语料组列表->生成处理cpsgrpVOS对象
        List<CpsgrpDO> cpsgrpDOS = cpsgrpManager.listByFilter(cpsgrpFilter, pageData);
        int totalCount = cpsgrpManager.countByFilter(cpsgrpFilter);
        List<CpsgrpVO> cpsgrpVOS = cpsgrpDOS.stream().map(this::beanProcess).collect(Collectors.toList());

        //查询当前语料组被几个所班级使用
        cpsgrpVOS.forEach(cpsgrpVO -> {
            Integer classCnt = classCpsgrpMapper.selectCount(new QueryWrapper<ClassCpsgrpDO>().eq("cpsgrp_id", cpsgrpVO.getId()));
            cpsgrpVO.setReleaseStatus(classCnt);
        });

        //TODO feign远程调用 获取userIds的user信息，将creator用realName进行展示
        pageData.setRecords(cpsgrpVOS);
        pageData.setTotal(totalCount);
        return pageData;
    }

    @Override
    public Object update(CpsgrpReq cpsgrpReq) {
        //更新语料组
        CpsgrpDO cpsgrpDO = cpsgrpMapper.selectById(cpsgrpReq.getId());
        if (cpsgrpDO == null) throw new BizException(BizCodeEnum.CPSGRP_NOT_EXIST);
        //全量更新cpsgrp,不更新：id, classId, null值
        BeanUtils.copyProperties(cpsgrpReq, cpsgrpDO, "id");
        cpsgrpDO.setGmtModified(null);
        int i = cpsgrpMapper.updateById(cpsgrpDO);
        cpsgrpDO = cpsgrpMapper.selectById(cpsgrpDO.getId());
        return cpsgrpDO;
    }

    @Override
    public Object genTranscript(TranscriptReq transcriptReq) {
//        //获取登录用户信息
//        LoginUser loginUser = LoginInterceptor.threadLocal.get();
//        if (loginUser == null) throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN);
//        //处理生成TranscriptDO对象
//        TranscriptDO transcriptDO = new TranscriptDO();
//        transcriptDO.setId("transcript_" + IDUtil.getSnowflakeId());
//        transcriptDO.setCpsgrpId(transcriptReq.getCpsgrpId());
//        transcriptDO.setRespondent(loginUser.getAccountNo());
//        //计算题目组总字数
//        StringBuilder allText = new StringBuilder();
//        CpsgrpVO cpsgrpVO = cpsgrpManager.selectDetailByCpsgrpId(transcriptReq.getCpsgrpId());
//        cpsgrpVO.getCpsrcdList().forEach(cpsrcdVO -> allText.append(cpsrcdVO.getRefText()));
//        int totalWords = CommonUtil.countWord(String.valueOf(allText));
//        //计算报告完整度得分
//        Double pronCompletion = computePronCompletion(transcriptReq.getScores());
//        transcriptDO.setPronCompletion(BigDecimal.valueOf(pronCompletion));
//        //计算报告准确度得分
//        Double pronAccuracy = computePronAccuracy(transcriptReq.getScores());
//        transcriptDO.setPronAccuracy(BigDecimal.valueOf(pronAccuracy));
//        //计算报告流畅性得分
//        Double pronFluency = computePronFluency(transcriptReq.getScores());
//        transcriptDO.setPronFluency(BigDecimal.valueOf(pronFluency));
//        //计算建议报告得分
//        Double suggestedScore = computeSuggestedScore(transcriptReq.getScores(), totalWords);
//        transcriptDO.setSuggestedScore(BigDecimal.valueOf(suggestedScore));
//        //插入数据库
//        int rows = transcriptMapper.insert(transcriptDO);
//        return transcriptMapper.selectById(transcriptDO.getId());
        return null;
    }


    /**
     * 计算报告建议得分
     */
    private Double computeSuggestedScore(List<ScoreDTO> scores, int totalWords) {
        int evalWordCount = scores.stream().mapToInt(ScoreDTO::getTotalWords).sum(); //参加评测的字数
        int unEvalWordCount = totalWords - evalWordCount;//未参加评测的字数
        int wrongWordCount = scores.stream().mapToInt(ScoreDTO::getWrongWords).sum(); //评测中的错字数
        double suggestedScore = 0.0;
        // 该语料组的总字数为0,语料组异常
        if (totalWords <= 0) throw new BizException(BizCodeEnum.CPSGRP_ERROR);
        suggestedScore = (1 - (wrongWordCount + unEvalWordCount) * 1.0 / totalWords) * 100;
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
    public CpsrcdVO beanProcess(TopicCpsDO topicCpsDO) {
        CpsrcdVO cpsrcdVO = new CpsrcdVO();
        CpsrcdDO cpsrcdDO = cpsrcdMapper.selectById(topicCpsDO.getCpsrcdId());
        if (cpsrcdDO == null) return null; //过滤掉已删除的题目
        BeanUtils.copyProperties(cpsrcdDO, cpsrcdVO);

        //聚合topicCpsDO类
        cpsrcdVO.setTopicId(topicCpsDO.getTopicId());
        cpsrcdVO.setCNum(topicCpsDO.getCNum());
        cpsrcdVO.setScore(topicCpsDO.getScore());
        cpsrcdVO.setEnablePinyin(topicCpsDO.getEnablePinyin());
        cpsrcdVO.setDescription(topicCpsDO.getDescription());

        //tags单独处理String->List<String>
        List<TaggingDO> taggingDOS = taggingMapper.selectList(new QueryWrapper<TaggingDO>().eq("entity_id", cpsrcdDO.getId()));
        if (taggingDOS.size() != 0) {
            List<Integer> tagIds = taggingDOS.stream().map(TaggingDO::getTagId).collect(Collectors.toList());
            List<TagDO> tagDOS = tagMapper.selectBatchIds(tagIds);
            List<String> tagNames = tagDOS.stream().map(TagDO::getName).collect(Collectors.toList());
            cpsrcdVO.setTags(tagNames);
        }
        return cpsrcdVO;
    }

    /**
     * CpsgrpDO->CpsgrpVO
     */
    private CpsgrpVO beanProcess(CpsgrpDO cpsgrpDO) {
        CpsgrpVO cpsgrpVO = new CpsgrpVO();
        JsonData course = userFeignService.getCourse("course_1654025145813176320");
        System.out.println(course);
        BeanUtils.copyProperties(cpsgrpDO, cpsgrpVO);
        return cpsgrpVO;
    }

    /**
     * CpsgrpDO->CpsgrpVO
     */
    private TopicVO beanProcess(TopicDO topicDO) {
        TopicVO topicVO = new TopicVO();
        BeanUtils.copyProperties(topicDO, topicVO);
        return topicVO;
    }
}
