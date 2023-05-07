package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.controller.group.Create;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CorpusReq {

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
     * 分值
     * */
    private Double wordWeight;

    /**
     * 示范音频url
     * */
    private String audioUrl;

    /**
     * 标签
     * */
    private String tags;

    /**
     * 汉语拼音
     */
    private String pinyin;

    /**
     * 参考文本
     */
    private String refText;

}
