package net.ecnu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.ecnu.model.CourseDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程信息 Mapper 接口
 * </p>
 *
 * @author TGX
 * @since 2023-03-01
 */
@Mapper
public interface CourseMapper extends BaseMapper<CourseDO> {
}
