package net.ecnu.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author LYW
 * @since 2023-05-15
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
     * 班级开始时间
     */
    private Date startTime;

    /**
     * 班级结束时间
     */
    private Date endTime;

    /**
     * 班级水平
     */
    private Integer level;

    /**
     * 班级图片，图片url
     */
    private String picture;

    /**
     * 班级公告
     */
    private String notice;

    /**
     * 限制学生人数
     */
    private String stuLimit;

    /**
     * 是否允许学生选课，0：不允许，1：允许，2：申请需验证
     */
    private Integer joinStatus;

    /**
     * 是否允许学生退课，0：不允许，1：允许，2：申请需验证
     */
    private Integer dropStatus;

    /**
     * 删除标识位
     */
    private Boolean del;

    /**
     * 班级创建时间
     */
    private Date gmtCreate;

    /**
     * 班级更新时间
     */
    private Date gmtModified;


}
