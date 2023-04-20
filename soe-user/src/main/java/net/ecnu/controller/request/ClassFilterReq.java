package net.ecnu.controller.request;

import lombok.Data;

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

    /**
     * 用户的accountNo
     */
    private String accountNo;

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


}
