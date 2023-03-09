package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.controller.group.Create;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class CourseFilterReq {
    /**
     * 课程id
     */
    @NotBlank(message = "必须指定课程id",groups = {Create.class})
    private String courseId;

    /**
     * 课程名
     */
    @NotBlank(message = "班级名字不能为空",groups = {Create.class})
    private String name;

    /**
     * 课程描述
     */
    private String description;

    /**
     * 课程分级
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
