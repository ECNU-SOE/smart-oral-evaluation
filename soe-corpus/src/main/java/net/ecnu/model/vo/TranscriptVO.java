package net.ecnu.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
public class TranscriptVO implements Serializable {

    /**
     * 评测报告id
     */
    private String id;

    /**
     * 关联语料组id
     */
    private String cpsgrpId;

    /**
     * 关联语料组名称
     */
    private String cpsgrpName;

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
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date gmtModified;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date gmtCreate;

}
