package net.ecnu.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CpsrcdReq {

    /**
     * 主键id
     */
    @NotEmpty(message = "id can't be empty in update", groups = {Update.class})
    private String id;

    /**
     * 语料原型id
     */
    private String corpusId;

    /**
     * 所属语料组id
     */
    @NotEmpty(message = "cpsgrpId can't be empty in add", groups = {Create.class})
    private String cpsgrpId;

    /**
     * 所属大题id
     */
    @NotEmpty(message = "topicId can't be empty in add", groups = {Create.class})
    private String topicId;

    /**
     * cpsrcd次序
     */
    @JsonProperty("cNum")
    private Integer cNum;

    /**
     * 评测模式
     */
    private Integer evalMode;

    /**
     * 语料难度
     */
    private Integer difficulty;

    /**
     * 每字分值
     */
    private BigDecimal wordWeight;

    /**
     * 语料内容拼音
     */
    private String pinyin;

    /**
     * 语料文本内容
     */
    private String refText;


}
