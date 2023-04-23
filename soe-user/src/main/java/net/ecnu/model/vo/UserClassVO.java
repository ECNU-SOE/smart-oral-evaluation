package net.ecnu.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
public class UserClassVO implements Serializable {

    private String accountNo;
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
    @JsonProperty
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
