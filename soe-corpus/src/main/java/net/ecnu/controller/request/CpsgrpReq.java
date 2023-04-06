package net.ecnu.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CpsgrpReq {

    /**
     * 语料组id
     */
    @NotEmpty(message = "id can't be empty in update", groups = {Update.class})
    private String id;

    /**
     * 所属课程id
     */
    private String courseId;

    /**
     * 语料组名称
     */
    @NotEmpty(message = "title can't be empty in add/update", groups = {Create.class, Update.class})
    private String title;

    /**
     * 语料组描述
     */
    @NotEmpty(message = "title can't be empty in add/update", groups = {Update.class})
    private String description;

    /**
     * 语料组类型
     */
    @NotNull(message = "type can't be null in add/update", groups = {Create.class, Update.class})
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
     * 起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

}
