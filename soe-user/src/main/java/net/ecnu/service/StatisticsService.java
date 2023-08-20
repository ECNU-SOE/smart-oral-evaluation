package net.ecnu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.model.dto.StatisticsDto;
import net.ecnu.model.vo.ClassCpsgrpInfoVo;
import net.ecnu.model.vo.StatisticsVo;

import java.util.List;

/**
 * @description:数据统计接口类
 * @Author lsy
 * @Date 2023/8/20 10:36
 */
public interface StatisticsService {


    Page<StatisticsVo> getStatisticsInfo(StatisticsDto statisticsDto);


    List<ClassCpsgrpInfoVo> getCpsgrpInfoByCourseId(String courseId,Integer currentTypeId);
}
