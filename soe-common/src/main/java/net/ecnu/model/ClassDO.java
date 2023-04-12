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
 * 
 * </p>
 *
 * @author TGX
 * @since 2023-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("class")
public class ClassDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班级id
     */
      @TableId(value = "id", type = IdType.NONE)
    private String id;

    /**
     * 班级所属课程id
     */
    private String courseId;

    /**
     * 班级名字，例：1班，2班
     */
    private String name;

    /**
     * 班级描述
     */
    private String description;

    /**
     * 班级创建者
     */
    private String creator;

    /**
     * 班级水平
     */
    private Integer level;

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
