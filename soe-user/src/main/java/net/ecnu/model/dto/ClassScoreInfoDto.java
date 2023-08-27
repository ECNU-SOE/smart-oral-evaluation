package net.ecnu.model.dto;

import lombok.Data;

/**
 * @description:班级学生成绩DTO类
 * @Author lsy
 * @Date 2023/8/27 11:33
 */
@Data
public class ClassScoreInfoDto {

    /**
     * 学生姓名
     * **/
    public String studentName;

    /**
     * 性别
     * **/
    public String sex;

    /**
     * 完整度得分
     * **/
    private Double pronCompletionScore;

    /**
     * 准确度得分
     * **/
    private Double pronAccuracyScore;

    /**
     * 流畅度得分
     * **/
    private Double pronFluencyScore;

    /**
     * 综合得分
     * **/
    private Double suggestedScoreScore;
}
