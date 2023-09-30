package net.ecnu.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Delete;
import net.ecnu.controller.group.Update;

import javax.validation.constraints.NotEmpty;

@Data
public class TopicCpsReq {

    /**
     * 题型id
     */
    @NotEmpty(message = "topicId can't be empty in add/del/update", groups = {Create.class, Delete.class, Update.class})
    private String topicId;

    /**
     * 子题id
     */
    @NotEmpty(message = "cpsrcdId can't be empty in add/del/update", groups = {Create.class, Delete.class, Update.class})
    private String cpsrcdId;

    /**
     * 子题序号
     */
    @JsonProperty("cNum")
    private Integer cNum;

    /**
     * 是否启用拼音
     */
    private Boolean enablePinyin;

    /**
     * 本题分值
     */
    private Double score;

    /**
     * 题目备注
     */
    private String description;

}
