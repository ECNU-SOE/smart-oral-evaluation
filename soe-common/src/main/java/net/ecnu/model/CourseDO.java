package net.ecnu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 课程表
 * </p>
 *
 * @author LYW
 * @since 2022-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("course")
public class CourseDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.NONE)
    private String id;

    /**
     * 课程名
     */
    private String name;

    /**
     * 班级id
     */
    private String classId;

    /**
     * 课程描述
     */
    private String description;

    /**
     * 课程分级
     */
    private String level;

    /**
     * 作业/考试开始时间
     */
    private Date startTime;

    /**
     * 作业/考试结束时间
     */
    private Date endTime;

    /**
     * 课程创建者
     */
    private String creator;

    /**
     * 删除标识位
     */
    private Boolean del;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;


}
