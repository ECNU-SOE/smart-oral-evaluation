package net.ecnu.service;

import net.ecnu.model.MistakeAudioDO;
import net.ecnu.model.MistakeAudioDOExample;
import net.ecnu.model.dto.MistakeAnswerDto;
import net.ecnu.model.dto.MistakeInfoDto;
import net.ecnu.model.dto.MistakesDto;
import net.ecnu.model.vo.MistakeAnswerVO;
import net.ecnu.model.vo.MistakeDetailVO;
import net.ecnu.model.vo.MistakesVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @Author lsy
 * @Date 2023/7/15 15:50
 */
@Service
public interface MistakeAudioService {

    long countByExample(MistakeAudioDOExample example);
    
    int deleteByExample(MistakeAudioDOExample example);

    int deleteByPrimaryKey(Long mistakeId);

    int insert(MistakeAudioDO record);

    int insertSelective(MistakeAudioDO record);

    List<MistakeAudioDO> selectByExample(MistakeAudioDOExample example);

    MistakeAudioDO selectByPrimaryKey(Long mistakeId);
    
    int updateByExampleSelective(MistakeAudioDO record, MistakeAudioDOExample example);

    int updateByExample(MistakeAudioDO record, MistakeAudioDOExample example);

    
    int updateByPrimaryKeySelective(MistakeAudioDO record);

    int updateByPrimaryKey(MistakeAudioDO record);

    MistakeDetailVO getDetail(Integer oneWeekKey);

    List<MistakesVO> getMistake(MistakesDto mistakesDto);

    MistakeAnswerVO checkAnswer(MistakeAnswerDto mistakeAnswer);

    Boolean isAddInErrorBook(String userId, MistakeInfoDto mistakeInfoDto, Double suggestedScore, Double questionScore);
}
