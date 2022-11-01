package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.controller.group.Create;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CorpusReq {

    /**
     * 语料类型
     */
    private Integer type;

    /**
     * 难易程度
     */
    private Integer level;

    /**
     * 汉语拼音
     */
    private String pinyin;

    /**
     * 参考文本
     */
    @NotEmpty(message = "refText can't be empty", groups = {Create.class})
    private String refText;

}
