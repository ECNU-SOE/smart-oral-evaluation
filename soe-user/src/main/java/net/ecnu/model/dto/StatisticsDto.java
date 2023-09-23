package net.ecnu.model.dto;

import lombok.Data;

/**
 * @description: 数据统计模块入参类
 * @Author lsy
 * @Date 2023/8/20 10:15
 */
@Data
public class StatisticsDto {

    /**
     * 课程id
     * **/
    private String courseId;

    /**
     * 作业、测评、考试id，不传则默认为该课程下第一场作业/测评/考试
     * **/
    private String cpsgrpId;

    /**
     * 完整度得分排序字段，0-正序，1-倒序，不传值不进行排序
     * **/
    private Integer pronCompletionKey;

    /**
     * 准确度得分排序字段，0-正序，1-倒序，不传值不进行排序
     * **/
    private Integer pronAccuracyKey;

    /**
     * 流畅度得分排序字段，0-正序，1-倒序，不传值不进行排序
     * **/
    private Integer pronFluencyKey;

    /**
     * 综合平均分排序字段，0-正序，1-倒序，不传值则按分数高->低排列
     * **/
    private Integer suggestedScoreKey;

    private Integer pageSize;

    private Integer pageNum;
}
