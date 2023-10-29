package net.ecnu.controller.request;

import lombok.Data;

import java.util.List;

@Data
public class CpsrcdFilterReq {
    
    /**
     * cpsrcdIds,限制cpsrcdId的查询范围
     */
    private List<String> cpsrcdIds;

    /**
     * 语料类型
     */
    private String type;

    /**
     * 起始难度
     */
    private Integer difficultyBegin;

    /**
     * 截止难度
     */
    private Integer difficultyEnd;

    /**
     * 文本内容
     */
    private String refText;

    /**
     * 标签列表
     */
    private List<Integer> tagIds;

}
