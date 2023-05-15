package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.controller.group.Update;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class CourUpdateReq {
    /**
     * 班级id
     */
    @NotBlank(message = "必须指定课程id", groups = {Update.class})
    private String id;

    /**
     * 课程名
     */
    private String name;

    /**
     * 课程描述
     */
    private String description;

    /**
     * 课程图片
     */
    private String picture;

    /**
     * 课程难度
     */
    private Integer difficulty;



}
