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
     * 所属班级id
     */
    private String classId;

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
    @NotNull(message = "type can't be null in add", groups = {Create.class})
    private Integer type;

    /**
     * 难易程度
     */
    private String difficulty;

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
