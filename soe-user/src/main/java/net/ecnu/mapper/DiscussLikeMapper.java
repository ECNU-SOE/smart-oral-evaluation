package net.ecnu.mapper;

import java.util.List;
import net.ecnu.model.DiscussLike;
import net.ecnu.model.DiscussLikeExample;
import org.apache.ibatis.annotations.Param;

/**
 * @description:
 * @Author lsy
 * @Date 2023/9/3 13:36
 */
public interface DiscussLikeMapper {
    long countByExample(DiscussLikeExample example);

    int deleteByExample(DiscussLikeExample example);

    /**
     * delete by primary key
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(DiscussLike record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(DiscussLike record);

    List<DiscussLike> selectByExample(DiscussLikeExample example);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    DiscussLike selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DiscussLike record, @Param("example") DiscussLikeExample example);

    int updateByExample(@Param("record") DiscussLike record, @Param("example") DiscussLikeExample example);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(DiscussLike record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(DiscussLike record);

    /**
     * 检查该用户是否有过对该帖的点赞记录
     * **/
    int isExistLikesRecord(@Param("discussId") Integer discussId,@Param("currentAccountNo") String currentAccountNo);
}