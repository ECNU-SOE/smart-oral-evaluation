package net.ecnu.controller.request;

import lombok.Data;

import java.util.Date;

@Data
public class ClassFilterReq {
    /**
     * 班级id
     */
    private String id;

    /**
     * 课程id
     */
    private String courseId;

    private String cpsgrpId;

    /**
     * 课程名
     */
    private String name;

    /**
     * 课程描述
     */
    private String description;

    /**
     * 课程创建者
     */
    private String creator;

    /**
     * 难易程度
     */
    private Integer level;

    private Integer joinStatus;

    private Integer dropStatus;


}
