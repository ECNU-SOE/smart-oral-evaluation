package net.ecnu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.ecnu.model.ClassDiscussDo;
import net.ecnu.model.vo.DiscussVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @Author lsy
 * @Date 2023/5/28 11:04
 */
@Mapper
public interface ClassDiscussMapper extends BaseMapper<ClassDiscussDo> {
    int deleteByPrimaryKey(Long discussId);

    int insert(ClassDiscussDo record);

    int insertSelective(ClassDiscussDo record);

    ClassDiscussDo selectByPrimaryKey(Long discussId);

    int updateByPrimaryKeySelective(ClassDiscussDo record);

    int updateByPrimaryKey(ClassDiscussDo record);

    int addLikes(String discussId);

}