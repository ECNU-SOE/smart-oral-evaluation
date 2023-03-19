package net.ecnu.controller.request;

import lombok.Data;

import java.util.Date;

@Data
public class CourFilterReq {
    /**
     * 班级id
     */
    private String id;

    /**
     * 课程id
     */
    private String courseId;

    /**
     * 课程名
     */
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

    /**
     * 课程创建者
     */
    private String creator;


}
