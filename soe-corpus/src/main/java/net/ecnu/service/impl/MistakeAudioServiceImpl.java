package net.ecnu.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.enums.MistakeTypeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.mapper.MistakeAudioMapper;
import net.ecnu.model.MistakeAudioDO;
import net.ecnu.model.MistakeAudioDOExample;
import net.ecnu.model.dto.MistakeAnswerDto;
import net.ecnu.model.dto.MistakesDto;
import net.ecnu.model.vo.MistakeAnswerVO;
import net.ecnu.model.vo.MistakeDetailVO;
import net.ecnu.model.vo.MistakeTypeVO;
import net.ecnu.model.vo.MistakesVO;
import net.ecnu.service.MistakeAudioService;
import net.ecnu.util.RequestParamUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
            String typeName = Objects.isNull(mistakeTypeVO.getMistakeTypeCode()) ? MistakeTypeEnum.DEFAULT.getMsg() : MistakeTypeEnum.getMsgByCode(mistakeTypeVO.getMistakeTypeCode());
            mistakeTypeVO.setMistakeTypeCode(Objects.isNull(mistakeTypeVO.getMistakeTypeCode()) ? MistakeTypeEnum.DEFAULT.getCode() : mistakeTypeVO.getMistakeTypeCode());
            mistakeTypeVO.setMistakeTypeName(typeName);
            //各题型的错题数
            Integer typeNum = Objects.isNull(mistakeTypeVO.getMistakeNum()) ? 0 : mistakeTypeVO.getMistakeNum();
            mistakeTypeVO.setMistakeNum(typeNum);
        }
        mistakeDetailVO.setMistakeTotalNumber(total);
        mistakeDetailVO.setStubbornMistakeNumber(stubbornMistakeNumber);
        return  mistakeDetailVO;
    }

    @Override
    public List<MistakesVO> getMistake(MistakesDto mistakesDto) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        List<MistakesVO> mistakesVOS = new ArrayList<>();
        //获取cpsrcd快照题目id
        List<String> cpsrcdIdList = mistakeAudioMapper.getCpsrcdIdByUserIdAndMistakeType(currentAccountNo
                ,mistakesDto.getMistakeTypeCode(),mistakesDto.getOneWeekKey());
        //查询对应的题目信息
        mistakesVOS = mistakeAudioMapper.getMistakesInfo(cpsrcdIdList);
        return mistakesVOS;
    }

    @Override
    public MistakeAnswerVO checkAnswer(MistakeAnswerDto mistakeAnswer) {
        MistakeAnswerVO mistakeAnswerVO = new MistakeAnswerVO();
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        Double resultRate = mistakeAnswer.getSuggestedScore() / mistakeAnswer.getQuestionScore();
        if (resultRate >= PASS_RATE) {
            //错题回答成功，将该错题逻辑删除，当数据量过大时，可手动删除数据库错题表中被逻辑删除数据
            if (mistakeAudioMapper.cleanMistakeByCpsrcdId(currentAccountNo,mistakeAnswer.getCpsrcdId()) <= 0) {
                throw new BizException(BizCodeEnum.MISTAKE_CLEAN_ERROR);
            }
            mistakeAnswerVO.setResultMsg("答对题目将从错题本移除");
            return mistakeAnswerVO;
        } else {
            //错题回答失败，增加该错题错误次数
            if (mistakeAudioMapper.addWrongNumByCpsrcdId(currentAccountNo, mistakeAnswer.getCpsrcdId()) <= 0) {
                throw new BizException(BizCodeEnum.MISTAKE_ADD_WRONG_NUM_ERROR);
            }
            mistakeAnswerVO.setResultMsg("回答错误");
            return mistakeAnswerVO;
        }
    }

    /**
     * 判断语音对错方法
     * @param userId 用户唯一id
     * @param cpsrcdId 语料快照id
     * @param suggestedScore 用户该题得分
     * @param questionScore 该题总分
     * **/
    @Override
    public Boolean isAddInErrorBook(String userId,String cpsrcdId,Integer questionType,Double suggestedScore,Double questionScore){
        Double resultRate = suggestedScore / questionScore;
        if (resultRate >= PASS_RATE) {
            //回答成功
            return true;
        } else {
            //回答失败，将该题加入用户的错题集
            MistakeAudioDO record = new MistakeAudioDO();
            record.setCreateTime(new Date()).setCpsrcdId(cpsrcdId).setErrorSum(1)
                    .setUserId(userId).setUpdateTime(new Date()).setMistakeType(questionType)
                    .setDelFlg(false);
            if (mistakeAudioMapper.insertSelective(record) <= 0) {
                throw new BizException(BizCodeEnum.MISTAKE_ADD_ERROR);
            }
            return false;
        }
    }

}
