package net.ecnu.mapper;

import net.ecnu.model.CourseDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程信息 Mapper 接口
 * </p>
 *
 * @author TGX
 * @since 2023-04-07
 */
@Mapper
public interface CourseMapper extends BaseMapper<CourseDO> {
}
