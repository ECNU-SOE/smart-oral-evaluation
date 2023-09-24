package net.ecnu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.ecnu.model.ClassDiscussDo;
import net.ecnu.model.ClassDiscussDoExample;
import net.ecnu.model.vo.DiscussVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @Author lsy
 * @Date 2023/5/28 11:04
 */
@Mapper
public interface ClassDiscussMapper extends BaseMapper<ClassDiscussDo> {
    long countByExample(ClassDiscussDoExample example);

    int deleteByExample(ClassDiscussDoExample example);

    /**
     * delete by primary key
     * @param discussId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long discussId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(ClassDiscussDo record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(ClassDiscussDo record);

    List<ClassDiscussDo> selectByExample(ClassDiscussDoExample example);

    /**
     * select by primary key
     * @param discussId primary key
     * @return object by primary key
     */
    ClassDiscussDo selectByPrimaryKey(Long discussId);

    int updateByExampleSelective(@Param("record") ClassDiscussDo record, @Param("example") ClassDiscussDoExample example);

    int updateByExample(@Param("record") ClassDiscussDo record, @Param("example") ClassDiscussDoExample example);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(ClassDiscussDo record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(ClassDiscussDo record);

    int addLikes(String discussId);

    List<Map<String, Object>> getPublisherInfo(@Param("publisher") String publisher,@Param("classId") String clsssId);

    Integer selectPublishClassesNum(@Param("forwardId") Long forwardId);

    int reduceLikes(@Param("discussId") String discussId);
}