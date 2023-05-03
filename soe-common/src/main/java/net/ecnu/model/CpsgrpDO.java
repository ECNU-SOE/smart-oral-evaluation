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
 * 语料组(作业、试卷、测验)
 * </p>
 *
 * @author LYW
 * @since 2023-05-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("cpsgrp")
public class CpsgrpDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 语料组id
     */
    @TableId(value = "id", type = IdType.NONE)
    private String id;

    /**
     * 所属课程id
     */
    private String classId;

    /**
     * 语料组名称
     */
    private String title;

    /**
     * 语料组描述
     */
    private String description;

    /**
     * 语料组类型：1-测验；2-试卷；3-作业
     */
    private Integer type;

    /**
     * 难易程度：-1未知；[A~J][0~9]
     */
    private String difficulty;

    /**
     * 公开类型：0-公开；1-私有
     */
    private Integer isPublic;

    /**
     * 起始时间
     */
    private Date startTime;

    /**
     * 截止时间
     */
    private Date endTime;

    /**
     * 创建者账号id
     */
    private String creator;

    /**
     * 逻辑删除位置
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
