package net.ecnu.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 课程信息
 * </p>
 *
 * @author TGX
 * @since 2023-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("course")
public class CourseDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.NONE)
    private String id;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 课程描述
     */
    private String description;

    /**
     * 创建者id
     */
    private String creator;

    /**
     * 逻辑删除标识位
     */
    private Boolean del;

    /**
     * 课程创建时间
     */
    private Date gmtCreate;

    /**
     * 课程更新时间
     */
    private Date gmtModified;


}
