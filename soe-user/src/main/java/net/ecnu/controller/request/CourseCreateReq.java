package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.controller.group.Create;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class CourseCreateReq {
    /**
     * 班级id
     */
    @NotBlank(message = "必须指定班级id",groups = {Create.class})
    private String id;

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
     * 班级分级
     */
    @NotBlank(message = "班级层级不能为空",groups = {Create.class})
    private Integer level;

    /**
     * 开始时间
     */
    @NotBlank(message = "课程开始时间不能为空",groups = {Create.class})
    private Date startTime;

    /**
     * 结束时间
     */
    @NotBlank(message = "课程结束时间不能为空",groups = {Create.class})
    private Date endTime;

}
