package net.ecnu.mapper;

import net.ecnu.model.MistakeAudioDO;
import net.ecnu.model.MistakeAudioDOExample;
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

    /**
     * delete by primary key
     * @param mistakeId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long mistakeId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(MistakeAudioDO record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(MistakeAudioDO record);

    List<MistakeAudioDO> selectByExample(MistakeAudioDOExample example);

    /**
     * select by primary key
     * @param mistakeId primary key
     * @return object by primary key
     */
    MistakeAudioDO selectByPrimaryKey(Long mistakeId);

    int updateByExampleSelective(@Param("record") MistakeAudioDO record, @Param("example") MistakeAudioDOExample example);

    int updateByExample(@Param("record") MistakeAudioDO record, @Param("example") MistakeAudioDOExample example);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(MistakeAudioDO record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(MistakeAudioDO record);

    Integer selectMistakeTotal(@Param("userId") String userId);

    Integer selectStubbornMistakeNumber(@Param("userId") String userId);

    List<MistakeTypeVO> selectEachMistakeTypeData(@Param("userId") String userId);

    List<MistakeTypeVO> selectEachMistakeTypeDataNearWeek(@Param("userId") String userId);

    List<String> getCpsrcdIdByUserIdAndMistakeType(@Param("userId") String userId
            , @Param("mistakeTypeCode") Integer mistakeTypeCode
            , @Param("oneWeekKey") Integer oneWeekKey);

    List<MistakesVO> getMistakesInfo(@Param("cpsrcdIdList") List<String> cpsrcdIdList);

    int cleanMistakeByCpsrcdId(@Param("userId") String userId,@Param("cpsrcdId") String cpsrcdId);

    int addWrongNumByCpsrcdId(@Param("userId") String userId,@Param("cpsrcdId") String cpsrcdId);

    Integer getQuestionType(@Param("cpsrcdId") String cpsrcdId);
}