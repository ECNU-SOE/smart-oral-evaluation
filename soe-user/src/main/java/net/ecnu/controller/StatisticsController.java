package net.ecnu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.enums.CpsgrpTypeEnum;
import net.ecnu.model.dto.StatisticsDto;
import net.ecnu.model.vo.ClassCpsgrpInfoVo;
import net.ecnu.model.vo.StatisticsVo;
import net.ecnu.service.StatisticsService;
import net.ecnu.util.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @description:数据统计模块
 * @Author lsy
 * @Date 2023/8/20 10:10
 */
@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;


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
        Integer currentTypeId = Objects.isNull(statisticsDto.getType()) ? CpsgrpTypeEnum.TEXT.getCode() : statisticsDto.getType();
        statisticsDto.setType(currentTypeId);
        //获取课程下的试题集数据
        List<ClassCpsgrpInfoVo> classCpsgrpInfoVos = statisticsService.getCpsgrpInfoByCourseId(statisticsDto.getCourseId(),currentTypeId);
        String currentCpsgrpId = "";
        if (!CollectionUtils.isEmpty(classCpsgrpInfoVos)) {
            currentCpsgrpId = StringUtils.isEmpty(statisticsDto.getCpsgrpId())
                    ? classCpsgrpInfoVos.get(0).getCpsgrpId() : statisticsDto.getCpsgrpId();
        }
        statisticsDto.setCpsgrpId(currentCpsgrpId);
        /**
         * 该课程下班级答题情况，分页
         * **/
        Page<StatisticsVo> pageInfo = statisticsService.getStatisticsInfo(statisticsDto);
        resultMap.put("pageInfo", pageInfo);
        resultMap.put("currentTypeId", currentTypeId);
        resultMap.put("typeList", CpsgrpTypeEnum.getAllTypeInfo());
        resultMap.put("currentCpsgrpId", currentCpsgrpId);
        resultMap.put("cpsgrpList", classCpsgrpInfoVos);
        return JsonData.buildSuccess(resultMap);
    }

    @GetMapping("/exportExcel")
    public JsonData exportExcel(@RequestParam("classId") String classId) {

        return JsonData.buildSuccess();
    }
}
