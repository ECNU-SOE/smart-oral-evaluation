package net.ecnu.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @description:完成度数据统计Vo类
 * @Author lsy
 * @Date 2023/9/10 20:58
 */
@Data
public class CompletionStatisticsVo {

    /**
     * 班级ID
     * **/
    private String classId;

    /**
     * 班级名称
     * **/
    private String className;

    /**
     * 已布置测评数
     */
    private Integer evaluationNums;

    /**
     * 已布置的考试数
     * **/
    private Integer examNums;

    /**
     * 班级人数
     * **/
    private Integer studentNums;

    /**
     * 测评完成度
     * **/
    List<CompleteClassStatistics> evaCompletionStatistics;

    /**
     * 考试完成度
     * **/
    List<CompleteClassStatistics> examCompletionStatistics;

}
