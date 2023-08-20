package net.ecnu.mapper;

import net.ecnu.model.ClassCpsgrpDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.ecnu.model.dto.StatisticsDto;
import net.ecnu.model.vo.ClassCpsgrpInfoVo;
import net.ecnu.model.vo.StatisticsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 课程-语料组 关系表 Mapper 接口
 * </p>
 *
 * @author LYW
 * @since 2023-07-09
 */
@Mapper
public interface ClassCpsgrpMapper extends BaseMapper<ClassCpsgrpDO> {

    List<ClassCpsgrpInfoVo> getCpsgrpInfoByCourseId(@Param("courseId") String courseId,@Param("currentTypeId") Integer currentTypeId);

    List<StatisticsVo> getStatisticsInfo(@Param("statisticsDto") StatisticsDto statisticsDto);
}
