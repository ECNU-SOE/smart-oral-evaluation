package net.ecnu.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Data
public class CpsgrpCreateReq {

    /**
     * 语料组名称
     */
    @NotEmpty(message = "name can't be empty")
    private String name;

    /**
     * 语料组类型
     */
    private Integer type;

    /**
     * 语料组描述
     */
    private String description;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * 题目组包含的语料原型
     */
    @NotEmpty(message = "cpsrcdList can't be empty")
    private List<String> corpusIds;

}
