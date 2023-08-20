package net.ecnu.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @description: 数据统计返回前端Vo类
 * @Author lsy
 * @Date 2023/8/20 10:42
 */
@Data
public class StatisticsVo {

    /**
     * 班级id
     * **/
    private String classId;

    /**
     * 班级名称
     * **/
    private String className;

    /**
     * 班级人数
     * **/
    private Integer studentNums;

    /**
     * 完整度平均分
     * **/
    private Double pronCompletionAverage;

    /**
     * 准确度平均分
     * **/
    private Double pronAccuracyAverage;

    /**
     * 流畅度平均分
     * **/
    private Double pronFluencyAverage;

    /**
     * 综合平均分
     * **/
    private Double suggestedScoreAverage;
}
