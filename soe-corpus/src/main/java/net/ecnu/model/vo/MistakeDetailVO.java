package net.ecnu.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @Author lsy
 * @Date 2023/7/15 16:08
 */
@Data
public class MistakeDetailVO {

    /**
     * 总错题数
     * **/
    private Integer MistakeTotalNumber;

    /**
     * 顽固错题数
     * **/
    private Integer StubbornMistakeNumber;

    /**
     * 各题型错题数
     * **/
    private List<MistakeTypeVO> eachMistakeTypeNumber;
}
