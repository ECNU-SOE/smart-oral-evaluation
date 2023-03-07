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
 * @since 2023-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("cpsgrp")
public class CpsgrpDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 语料组id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 语料组类型
     */
    private Integer type;

    /**
     * 难易程度
     */
    private Integer level;

    /**
     * 语料组名称
     */
    private String name;

    /**
     * 语料组描述
     */
    private String description;

    /**
     * 创建者账号id
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;


}
