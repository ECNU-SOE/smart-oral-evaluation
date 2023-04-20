package net.ecnu.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
public class ClassVO_LYW implements Serializable {

    /**
     * 班级id
     */
    private String classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 所属课程Id
     */
    private String courseId;

    /**
     * 所属课程名称
     */
    private String courseName;

    /**
     * 班级描述
     */
    private String description;

    /**
     * 班级水平
     */
    private Integer level;

    /**
     * 当前用户在班级中的角色
     */
    private Integer rType;

    /**
     * 班级创建时间
     */
    private Date gmtCreate;

    /**
     * 班级更新时间
     */
    private Date gmtModified;


}
