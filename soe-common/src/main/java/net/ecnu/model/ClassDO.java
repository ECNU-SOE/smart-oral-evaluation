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
 * @since 2023-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("class")
public class ClassDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班级id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 班级所属课程id
     */
    private String courseId;

    /**
     * 班级描述
     */
    private String description;

    /**
     * 班级水平
     */
    private Integer level;

    /**
     * 开课时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 删除标识位
     */
    private Integer del;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;


}
