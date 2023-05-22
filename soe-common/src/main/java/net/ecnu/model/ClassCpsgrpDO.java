package net.ecnu.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 课程-语料组 关系表
 * </p>
 *
 * @author LYW
 * @since 2023-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("class_cpsgrp")
public class ClassCpsgrpDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 课程id
     */
    private String classId;

    /**
     * 语料组id
     */
    private String cpsgrpId;

    /**
     * 语料组类型：1作业、2测验、3试卷
     */
    private Integer type;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 截止时间
     */
    private Date endTime;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;


}
