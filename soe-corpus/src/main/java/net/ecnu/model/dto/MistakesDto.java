package net.ecnu.model.dto;

import lombok.Data;

/**
 * @description:
 * @Author lsy
 * @Date 2023/7/15 18:37
 */
@Data
public class MistakesDto {

    /**
     * 题目类型
     * **/
    private Integer mistakeTypeCode;

    /**
     * 是否为近一周数据,0-不是，1-是
     * */
    private Integer oneWeekKey;
}
