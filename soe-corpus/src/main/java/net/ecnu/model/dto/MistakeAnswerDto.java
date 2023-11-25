package net.ecnu.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description:
 * @Author lsy
 * @Date 2023/7/16 16:34
 */
@Data
public class MistakeAnswerDto {

    /**
     * 题目的快照id
     * **/
    @NotEmpty
    private String cpsrcdId;

    /**
     * topic_cps.id 关联字段
     * **/
    private Integer topicCpsId;

    /**
     * 题目组id
     * **/
    private String cpsgrpId;

    /**
     * 得分
     * **/
    @NotNull
    private Double suggestedScore;

    /**
     * 该题分值
     * **/
    @NotNull
    private Double questionScore;
}
