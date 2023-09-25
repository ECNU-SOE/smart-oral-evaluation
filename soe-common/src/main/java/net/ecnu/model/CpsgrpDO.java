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
 * @since 2023-05-21
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
     * 语料组名称
     */
    private String title;

    /**
     * 语料组描述
     */
    private String description;

    /**
     * 难易程度：[A~J][0~9]
     */
    private String difficulty;

    /**
     * 公开类型：0公开、1私有
     */
    private Integer isPrivate;

    /**
     * 修改状态：0允许修改、1允许创建者修改、2不允许修改
     */
    private Integer modStatus;

    private String tags;

    /**
     * 创建者账号id
     */
    private String creator;

    /**
     * 逻辑删除标识位
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
