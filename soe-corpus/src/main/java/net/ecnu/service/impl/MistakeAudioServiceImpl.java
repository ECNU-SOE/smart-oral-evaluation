package net.ecnu.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.enums.QuestionTypeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.mapper.MistakeAudioMapper;
import net.ecnu.mapper.TaggingMapper;
import net.ecnu.mapper.TopicCpsMapper;
import net.ecnu.model.MistakeAudioDO;
import net.ecnu.model.MistakeAudioDOExample;
import net.ecnu.model.TopicCpsDO;
import net.ecnu.model.dto.MistakeAnswerDto;
import net.ecnu.model.dto.MistakeInfoDto;
import net.ecnu.model.dto.MistakesDto;
import net.ecnu.model.vo.MistakeAnswerVO;
import net.ecnu.model.vo.MistakeDetailVO;
import net.ecnu.model.vo.MistakeTypeVO;
import net.ecnu.model.vo.MistakesVO;
import net.ecnu.service.MistakeAudioService;
import net.ecnu.util.RequestParamUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @Author lsy
 * @Date 2023/7/15 16:22
 */
@Slf4j
@Service
public class MistakeAudioServiceImpl implements MistakeAudioService {

    @Resource
    private MistakeAudioMapper mistakeAudioMapper;
    @Resource
    private TaggingMapper taggingMapper;

    @Resource
    private TopicCpsMapper topicCpsMapper;

    @Value("${pass.rate}")
    private Double PASS_RATE;

    public long countByExample(MistakeAudioDOExample example) {
        return mistakeAudioMapper.countByExample(example);
    }


    public int deleteByExample(MistakeAudioDOExample example) {
        return mistakeAudioMapper.deleteByExample(example);
    }


    public int deleteByPrimaryKey(Long mistakeId) {
        return mistakeAudioMapper.deleteByPrimaryKey(mistakeId);
    }


    public int insert(MistakeAudioDO record) {
        return mistakeAudioMapper.insert(record);
    }


    public int insertSelective(MistakeAudioDO record) {
        return mistakeAudioMapper.insertSelective(record);
    }


    public List<MistakeAudioDO> selectByExample(MistakeAudioDOExample example) {
        return mistakeAudioMapper.selectByExample(example);
    }


    public MistakeAudioDO selectByPrimaryKey(Long mistakeId) {
        return mistakeAudioMapper.selectByPrimaryKey(mistakeId);
    }


    public int updateByExampleSelective(MistakeAudioDO record, MistakeAudioDOExample example) {
        return mistakeAudioMapper.updateByExampleSelective(record,example);
    }


    public int updateByExample(MistakeAudioDO record,MistakeAudioDOExample example) {
        return mistakeAudioMapper.updateByExample(record,example);
    }


    public int updateByPrimaryKeySelective(MistakeAudioDO record) {
        return mistakeAudioMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(MistakeAudioDO record) {
        return mistakeAudioMapper.updateByPrimaryKey(record);
    }

    @Override
    public MistakeDetailVO getDetail(Integer oneWeekKey) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        MistakeDetailVO mistakeDetailVO = new MistakeDetailVO();
        //获取错题总数
        Integer total = mistakeAudioMapper.selectMistakeTotal(currentAccountNo);
        //获取顽固错题数量
        Integer stubbornMistakeNumber = mistakeAudioMapper.selectStubbornMistakeNumber(currentAccountNo);
        List<MistakeTypeVO> mistakeTypeVOList = new ArrayList<>();
        if (oneWeekKey.intValue() == 0) {
            //获取各类题型错题数据
            mistakeTypeVOList = mistakeAudioMapper.selectEachMistakeTypeData(currentAccountNo);
        } else {
            //获取近一周各类题型错题数据
            mistakeTypeVOList = mistakeAudioMapper.selectEachMistakeTypeDataNearWeek(currentAccountNo);
        }
        for (MistakeTypeVO mistakeTypeVO : mistakeTypeVOList) {
            //题型名称
            String typeName = Objects.isNull(mistakeTypeVO.getMistakeTypeCode()) ? QuestionTypeEnum.OTHERS.getMsg() : QuestionTypeEnum.getMsgByCode(mistakeTypeVO.getMistakeTypeCode());
            mistakeTypeVO.setMistakeTypeCode(Objects.isNull(mistakeTypeVO.getMistakeTypeCode()) ? QuestionTypeEnum.OTHERS.getCode() : mistakeTypeVO.getMistakeTypeCode());
            mistakeTypeVO.setMistakeTypeName(typeName);
            //各题型的错题数
            Integer typeNum = Objects.isNull(mistakeTypeVO.getMistakeNum()) ? 0 : mistakeTypeVO.getMistakeNum();
            mistakeTypeVO.setMistakeNum(typeNum);
        }
        mistakeDetailVO.setEachMistakeTypeNumber(mistakeTypeVOList);
        mistakeDetailVO.setMistakeTotalNumber(total);
        mistakeDetailVO.setStubbornMistakeNumber(stubbornMistakeNumber);
        return  mistakeDetailVO;
    }

    @Override
    public List<MistakesVO> getMistake(MistakesDto mistakesDto) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        List<MistakesVO> randomMistakesVOS = new ArrayList<>();
        List<MistakesVO> cpsgrpMistakesVOS = new ArrayList<>();
        //获取cpsrcd快照题目id
        List<MistakeInfoDto> mistakeInfoList = mistakeAudioMapper.getCpsrcdIdByUserIdAndMistakeType(currentAccountNo
                ,mistakesDto.getMistakeTypeCode(),mistakesDto.getOneWeekKey());
        /**有些题目是随机一题来的，没有cpsgrpId,需要区分开**/
        //查询对应的题目信息
        if (!CollectionUtils.isEmpty(mistakeInfoList)) {
            List<MistakeInfoDto> randomList = mistakeInfoList.stream().filter(t -> Objects.isNull(t.getTopicCpsId())).collect(Collectors.toList());
            List<MistakeInfoDto> grpList = mistakeInfoList.stream().filter(t -> Objects.nonNull(t.getTopicCpsId())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(randomList)){
                randomMistakesVOS = mistakeAudioMapper.getMistakesInfo(randomList);
            }
            if(!CollectionUtils.isEmpty(grpList)){
                cpsgrpMistakesVOS = mistakeAudioMapper.getGrpMistakesInfo(grpList);
            }
        }
        List<MistakesVO> mistakesVOS = new ArrayList<>();
        mistakesVOS.addAll(randomMistakesVOS);
        mistakesVOS.addAll(cpsgrpMistakesVOS);
        //获取题目对应的tag信息
        for (MistakesVO mistakesVO : mistakesVOS) {
            if (!StringUtils.isEmpty(mistakesVO.getCpsrcdId())){
                List<String> tagList = taggingMapper.getTagInfoByCpsrcdId(mistakesVO.getCpsrcdId());
                mistakesVO.setTagList(tagList);
            }
        }
        return mistakesVOS;
    }

    @Override
    public MistakeAnswerVO checkAnswer(MistakeAnswerDto mistakeAnswer) {
        MistakeAnswerVO mistakeAnswerVO = new MistakeAnswerVO();
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        MistakeInfoDto mistakeInfoDto = new MistakeInfoDto();
        mistakeInfoDto.setTopicCpsId(mistakeAnswer.getTopicCpsId());
        if (this.isAddInErrorBook(currentAccountNo, mistakeInfoDto, mistakeAnswer.getSuggestedScore(), mistakeAnswer.getQuestionScore())) {
            mistakeAnswerVO.setResultMsg("答对题目将从错题本移除");
            return mistakeAnswerVO;
        } else {
            mistakeAnswerVO.setResultMsg("回答错误");
            return mistakeAnswerVO;
        }
    }

    /**
     * 判断语音对错方法
     * @param userId 用户唯一id
     * @param mistakeInfoDto 错题信息
     * @param suggestedScore 用户该题得分
     * @param questionScore 该题总分
     * **/
    @Override
    public Boolean isAddInErrorBook(String userId,MistakeInfoDto mistakeInfoDto,Double suggestedScore,Double questionScore){
        Double resultRate = suggestedScore / questionScore;
        if (resultRate >= PASS_RATE) {
            //回答成功
            return true;
        } else {
            TopicCpsDO topicCpsDO = topicCpsMapper.selectById(mistakeInfoDto.getTopicCpsId());
            if (Objects.isNull(topicCpsDO)) {
                throw new BizException(BizCodeEnum.TOPIC_CPS_UNEXIST);
            }
            //回答失败，将该题加入用户的错题集
            String questionTypeName = mistakeAudioMapper.getQuestionType(topicCpsDO.getCpsrcdId());
            /*if (Objects.isNull(questionType) || questionType < 0) {
                //没有查询到它的题目类型，则默认为0语音评测
                questionType = 0;
            }*/
            Integer questionType = QuestionTypeEnum.OTHERS.getCode();
            if (!StringUtils.isEmpty(questionTypeName)) {
                questionType = QuestionTypeEnum.getCodeByMsg(questionTypeName);
            }
            /**查询是否已加入错题本**/
            MistakeAudioDOExample mistakeAudioDOExample = new MistakeAudioDOExample();
            mistakeAudioDOExample.createCriteria().andUserIdEqualTo(userId).andTopicCpsIdEqualTo(mistakeInfoDto.getTopicCpsId())
                    .andDelFlgEqualTo(false);
            List<MistakeAudioDO> mistakeAudioDOS = mistakeAudioMapper.selectByExample(mistakeAudioDOExample);
            if (CollectionUtils.isEmpty(mistakeAudioDOS)) {
                MistakeAudioDO record = new MistakeAudioDO();
                record.setCreateTime(new Date()).setTopicCpsId(mistakeInfoDto.getTopicCpsId())
                        .setTopicCpsId(mistakeInfoDto.getTopicCpsId()).setErrorSum(1)
                        .setUserId(userId).setUpdateTime(new Date()).setMistakeType(questionType)
                        .setDelFlg(false);
                if (mistakeAudioMapper.insertSelective(record) <= 0) {
                    throw new BizException(BizCodeEnum.MISTAKE_ADD_ERROR);
                }
            } else {
                MistakeAudioDO mistakeAudioDO = mistakeAudioDOS.get(0);
                mistakeAudioDO.setTopicCpsId(mistakeInfoDto.getTopicCpsId());
                //答题错误次数增加
                mistakeAudioDO.setErrorSum(mistakeAudioDO.getErrorSum() + 1);
                //更新时间
                mistakeAudioDO.setUpdateTime(new Date());
                mistakeAudioMapper.updateByPrimaryKey(mistakeAudioDO);
            }
            return false;
        }
    }

}
