package net.ecnu.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 成绩单
 * </p>
 *
 * @author LYW
 * @since 2023-03-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("transcript")
public class TranscriptDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评测报告id
     */
    @TableId(value = "id", type = IdType.NONE)
    private String id;

    /**
     * 关联语料组id
     */
    private String cpsgrpId;

    /**
     * 答题人id
     */
    private String respondent;

    /**
     * 完整度得分
     */
    private BigDecimal pronCompletion;

    /**
     * 准确度得分
     */
    private BigDecimal pronAccuracy;

    /**
     * 流畅度得分
     */
    private BigDecimal pronFluency;

    /**
     * 系统建议得分
     */
    private BigDecimal suggestedScore;

    /**
     * 人工评测得分
     */
    private BigDecimal manualScore;

    /**
     * json格式报告
     */
    private String resJson;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;


}
