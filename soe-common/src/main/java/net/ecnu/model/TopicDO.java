package net.ecnu.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author LYW
 * @since 2023-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("topic")
public class TopicDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 题目类型id
     */
    @TableId(value = "id", type = IdType.NONE)
    private String id;

    /**
     * 所属题目组id
     */
    private String cpsgrpId;

    /**
     * topic次序
     */
    @JsonProperty("tNum")
    private Integer tNum;

    /**
     * 题目名称
     */
    private String title;

    /**
     * 所占分值
     */
    private Double score;

    /**
     * 大题难度
     */
    private Integer difficulty;

    /**
     * 题目备注说明
     */
    private String description;


}
