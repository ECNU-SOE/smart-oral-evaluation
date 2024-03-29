package net.ecnu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.model.dto.CompletionStatisticsReq;
import net.ecnu.model.dto.ScoreStatisticsReq;
import net.ecnu.model.dto.StatisticsDto;
import net.ecnu.model.vo.CompletionStatisticsVo;
import net.ecnu.model.vo.ScoreStatisticsVo;
import net.ecnu.model.vo.StatisticsVo;
import net.ecnu.service.StatisticsService;
import net.ecnu.util.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @description:数据统计模块
 * @Author lsy
 * @Date 2023/8/20 10:10
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;

    /**
     * 获取课程下的班级、测评下拉框数据
     * **/
    @GetMapping("/getOptions")
    public JsonData getOptions(@RequestParam("courseId") String courseId) {
        if(StringUtils.isEmpty(courseId)){
            return JsonData.buildError("课程id不能为空");
        }
        Map<String,Object> optionsInfoMap = statisticsService.getOptionsInfo(courseId);
        return JsonData.buildSuccess(optionsInfoMap);
    }

    /**
     * 课程下的考试、测验成绩分析
     **/
    @PostMapping("/scoreStatistics")
    public JsonData scoreStatistics(@RequestBody ScoreStatisticsReq scoreStatisticsReq) {
        if (StringUtils.isEmpty(scoreStatisticsReq.getCourseId())) {
            return JsonData.buildError("课程id不能为空");
        }
        if(StringUtils.isEmpty(scoreStatisticsReq.getCpsgrpId())){
            return JsonData.buildError("语料组id不能为空");
        }
        ScoreStatisticsVo scoreStatisticsVo = statisticsService.scoreStatistics(scoreStatisticsReq);
        return JsonData.buildSuccess(scoreStatisticsVo);
    }

    /**
     * 班级测评/考试的完成率统计
     * **/
    @PostMapping("/completionStatistics")
    public JsonData completionStatistics(@RequestBody CompletionStatisticsReq completionStatisticsReq) {
        if (StringUtils.isEmpty(completionStatisticsReq.getCourseId())) {
            return JsonData.buildError("课程id不能为空");
        }
        if (StringUtils.isEmpty(completionStatisticsReq.getClassId())) {
            return JsonData.buildError("班级id不能为空");
        }
        CompletionStatisticsVo completionStatisticsVo = statisticsService.completionStatistics(completionStatisticsReq);
        return JsonData.buildSuccess(completionStatisticsVo);
    }


    /**
     * 课程测验、考试数据统计
     **/
    @PostMapping("/dataStatistics")
    public JsonData dataStatistics(@RequestBody StatisticsDto statisticsDto) {
        if (StringUtils.isEmpty(statisticsDto.getCourseId())) {
            return JsonData.buildError("课程id不能为空");
        }
        if (Objects.isNull(statisticsDto.getPageNum())) {
            statisticsDto.setPageNum(1);
        }
        if (Objects.isNull(statisticsDto.getPageSize())) {
            statisticsDto.setPageSize(10);
        }
        Map<String, Object> resultMap = new HashMap<>();
        /**
         * 该课程下班级答题情况，分页
         * **/
        Page<StatisticsVo> pageInfo = statisticsService.getStatisticsInfo(statisticsDto);
        resultMap.put("pageInfo", pageInfo);
        return JsonData.buildSuccess(resultMap);
    }

    /**
     * 导出班级下每次测验的学生成绩
     **/
    @GetMapping("/exportExcel")
    public void exportExcel(@RequestParam("classId") String classId, @RequestParam("cpsgrpId") String cpsgrpId, HttpServletResponse response) {
        if (StringUtils.isEmpty(classId)) {
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY);
        }
        try {
            statisticsService.exportExcel(classId, cpsgrpId, response);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
