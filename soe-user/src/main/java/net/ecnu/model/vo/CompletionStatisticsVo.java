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

    private String classId;

    /**
     * 已布置测评数
     */
    private Integer evaluationNums;

    /**
     * 已布置的考试数
     * **/
    private Integer examNums;

    /**
     * 测评、考试完成度
     * **/
    List<CompleteClassStatistics> completeClassStatistics;
}
