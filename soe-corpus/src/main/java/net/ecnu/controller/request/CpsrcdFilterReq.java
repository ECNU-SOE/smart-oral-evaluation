package net.ecnu.controller.request;

import lombok.Data;

@Data
public class CpsrcdFilterReq {

    /**
     * 语料id
     * */
    private String cpsrcdId;

    /**
     * 评测模式
     */
    private Integer evalMode;

    private String type;

    /**
     * 难易程度
     */
    private Integer difficulty;



    /**
     * 文本内容
    * */
    private String textValue;

    private String tagName;

}
