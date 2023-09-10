package net.ecnu.model.vo.dto;

import lombok.Data;

/**
 * @description:班级下的考试/测验的成绩分析
 * @Author lsy
 * @Date 2023/9/10 11:23
 */
@Data
public class ClassScoreAnalysis {

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
    private Integer classSize;

    /**
     * 目前答题人数
     * **/
    private Integer currentRespondentNums;

    /**
     * 最低分
     * **/
    private Double lowestScore;

    /**
     * 最高分
     * **/
    private Double highestScore;

    /**
     * 平均分
     * **/
    private Double averageScore;

    /**
     * 不及格人数
     * **/
    private Integer failNums;

    /**
     * 及格人数
     * **/
    private Integer passNums;
}
