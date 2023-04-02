package net.ecnu.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.ecnu.controller.group.Create;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class CpsgrpReq {

    /**
     * 语料组id
     */
    private String id;

    /**
     * 所属课程id
     */
    private String courseId;

    /**
     * 语料组名称
     */
    @NotEmpty(message = "title can't be empty in add", groups = {Create.class})
    private String title;

    /**
     * 语料组描述
     */
    private String description;

    /**
     * 语料组类型
     */
    private Integer type;

    /**
     * 难易程度
     */
    private Integer difficulty;

    /**
     * 公开类型
     */
    private Integer isPublic;

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

}
