package net.ecnu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.ecnu.model.CourseDiscussDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @Author lsy
 * @Date 2023/5/21 16:22
 */
@Mapper
public interface CourseDiscussMapper extends BaseMapper<CourseDiscussDo> {
    int deleteByPrimaryKey(Long discussId);

    int insert(CourseDiscussDo record);

    int insertSelective(CourseDiscussDo record);

    CourseDiscussDo selectByPrimaryKey(Long discussId);

    int updateByPrimaryKeySelective(CourseDiscussDo record);

    int updateByPrimaryKey(CourseDiscussDo record);

    int addLikes(String discussId);
}