package net.ecnu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.model.dto.CompletionStatisticsReq;
import net.ecnu.model.dto.ScoreStatisticsReq;
import net.ecnu.model.dto.StatisticsDto;
import net.ecnu.model.vo.ClassCpsgrpInfoVo;
import net.ecnu.model.vo.CompletionStatisticsVo;
import net.ecnu.model.vo.ScoreStatisticsVo;
import net.ecnu.model.vo.StatisticsVo;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @description:数据统计接口类
 * @Author lsy
 * @Date 2023/8/20 10:36
 */
public interface StatisticsService {


    Page<StatisticsVo> getStatisticsInfo(StatisticsDto statisticsDto);


    List<ClassCpsgrpInfoVo> getCpsgrpInfoByCourseId(String courseId,Integer currentTypeId);

    void exportExcel(String classId,String cpsgrpId, HttpServletResponse response) throws IOException;

    Map<String, Object> getOptionsInfo(String courseId);

    ScoreStatisticsVo scoreStatistics(ScoreStatisticsReq scoreStatisticsReq);

    CompletionStatisticsVo completionStatistics(CompletionStatisticsReq completionStatisticsReq);
}
