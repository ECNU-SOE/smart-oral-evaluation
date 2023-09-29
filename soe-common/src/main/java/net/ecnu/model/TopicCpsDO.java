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
 * @author LYW
 * @since 2023-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("topic_cps")
public class TopicCpsDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 题型id
     */
    private String topicId;

    /**
     * 子题id
     */
    private String cpsrcdId;

    /**
     * 子题序号
     */
    private Integer cNum;

    /**
     * 是否启用拼音
     */
    private Boolean enablePinyin;

    /**
     * 本题分值
     */
    private Double score;

    /**
     * 题目备注
     */
    private String description;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;


}
