package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class CourseUpdateReq {
    /**
     * 班级id
     */
    @NotBlank(message = "必须指定班级id",groups = {Update.class})
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
     * 班级分级
     */
    private Integer level;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

}
