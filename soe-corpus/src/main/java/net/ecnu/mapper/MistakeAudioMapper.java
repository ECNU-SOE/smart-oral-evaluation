package net.ecnu.mapper;

import net.ecnu.model.MistakeAudioDO;
import net.ecnu.model.MistakeAudioDOExample;
import net.ecnu.model.TopicCpsDO;
import net.ecnu.model.dto.MistakeInfoDto;
import net.ecnu.model.vo.MistakeTypeVO;
import net.ecnu.model.vo.MistakesVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @Author lsy
 * @Date 2023/7/15 15:50
 */
public interface MistakeAudioMapper {
    long countByExample(MistakeAudioDOExample example);

    int deleteByExample(MistakeAudioDOExample example);

    int deleteByPrimaryKey(Long mistakeId);

    int insert(MistakeAudioDO record);

    int insertSelective(MistakeAudioDO record);

    List<MistakeAudioDO> selectByExample(MistakeAudioDOExample example);

    MistakeAudioDO selectByPrimaryKey(Long mistakeId);

    int updateByExampleSelective(@Param("record") MistakeAudioDO record, @Param("example") MistakeAudioDOExample example);

    int updateByExample(@Param("record") MistakeAudioDO record, @Param("example") MistakeAudioDOExample example);

    int updateByPrimaryKeySelective(MistakeAudioDO record);

    int updateByPrimaryKey(MistakeAudioDO record);

    int updateBatch(List<MistakeAudioDO> list);

    int batchInsert(@Param("list") List<MistakeAudioDO> list);

    Integer selectMistakeTotal(@Param("userId") String userId);

    Integer selectStubbornMistakeNumber(@Param("userId") String userId);

    List<MistakeTypeVO> selectEachMistakeTypeData(@Param("userId") String userId);

    List<MistakeTypeVO> selectEachMistakeTypeDataNearWeek(@Param("userId") String userId);

    List<MistakeInfoDto> getCpsrcdIdByUserIdAndMistakeType(@Param("userId") String userId
            , @Param("mistakeTypeCode") Integer mistakeTypeCode
            , @Param("oneWeekKey") Integer oneWeekKey);

    List<MistakesVO> getMistakesInfo(@Param("mistakeInfoList") List<MistakeInfoDto> mistakeInfoList);

    int cleanMistakeByCpsrcdId(@Param("userId") String userId,@Param("cpsrcdId") String cpsrcdId,@Param("cpsgrpId")String cpsgrpId);

    int addWrongNumByCpsrcdId(@Param("userId") String userId,@Param("cpsrcdId") String cpsrcdId,@Param("cpsgrpId")String cpsgrpId);

    String getQuestionType(@Param("cpsrcdId") String cpsrcdId);

    List<MistakesVO> getGrpMistakesInfo(@Param("topicCpsrcdList") List<MistakeInfoDto> topicCpsrcdList);

    List<MistakeAudioDO> getMistakesInfoByCpsrcdIdOrTopcpsId(@Param("userId") String userId,@Param("topicCpsDO") TopicCpsDO topicCpsDO);
}