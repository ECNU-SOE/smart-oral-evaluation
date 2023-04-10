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
     * 班级名
     */
    private String name;

    /**
     * 详细描述
     */
    private String description;

    /**
     * 课程创建者
     */
    private String creator;

    private Date gmtCreate;

    private Date gmtModified;


}
