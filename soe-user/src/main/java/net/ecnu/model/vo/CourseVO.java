package net.ecnu.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * course快照
 * </p>
 *
 * @author TGX
 * @since 2023-3-8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CourseVO implements Serializable {

    /**
     * 班级id
     */
    private String id;

    /**
     * 课程id
     */
    private String courseId;

    /**
     * 班级名
     */
    private String name;

    /**
     * 详细描述
     */
    private String description;

    /**
     * 班级层级
     */
    private Integer level;

    /**
     * 课程开始时间
     */
    private Date startTime;

    /**
     * 课程结束时间
     */
    private Date endTime;

    /**
     * 课程创建者
     */
    private String creator;


}
