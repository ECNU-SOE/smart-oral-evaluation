package net.ecnu.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class CpsgrpReq {

    /**
     * 语料组id
     */
    @NotEmpty(message = "id can't be empty in update", groups = {Update.class})
    private String id;

    /**
     * 语料组名称
     */
    @NotEmpty(message = "title can't be empty in create", groups = {Create.class})
    private String title;

    /**
     * 语料组描述
     */
    private String description;


    /**
     * 难易程度
     */
    private String difficulty;

    /**
     * 公开类型
     */
    private Integer isPrivate;

    private Integer modStatus;

    private String tags;


}
