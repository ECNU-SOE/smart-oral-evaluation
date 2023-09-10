package net.ecnu.mapper;

import net.ecnu.model.ClassDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.ecnu.model.vo.dto.ClassOptions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author TGX
 * @since 2023-04-10
 */
@Mapper
public interface ClassMapper extends BaseMapper<ClassDO> {

    List<ClassOptions> getOptionsInfo(@Param("courseId") String courseId);
}
