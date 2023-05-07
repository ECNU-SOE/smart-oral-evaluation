package net.ecnu.controller.request;

import lombok.Data;

@Data
public class CorpusFilterReq {

    /**
     * 语料id
     * */
    private String corpusId;

    /**
     * 评测模式
     */
    private Integer evalMode;

    /**
     * 难易程度
     */
    private Integer difficulty;

    /**
     * 文本内容
    * */
    private String textValue;

}
