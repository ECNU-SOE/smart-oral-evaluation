package net.ecnu.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * corpus快照
 * </p>
 *
 * @author LYW
 * @since 2022-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EvalResultVO implements Serializable {

    /**
     * 字数总计
     */
    private Integer totalWordCount;

    /**
     * 错字总计
     */
    private Integer wrongWordCount;

    /**
     * 系统建议得分
     */
    private Double suggestedScore;

    /**
     * 准确度得分
     */
    private Double pronAccuracy;

    /**
     * 流畅性得分
     */
    private Double pronFluency;

    /**
     * 完整性得分
     */
    private Double pronCompletion;


}
