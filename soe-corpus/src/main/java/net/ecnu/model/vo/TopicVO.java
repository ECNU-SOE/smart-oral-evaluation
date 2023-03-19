package net.ecnu.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Topic大题
 * </p>
 *
 * @author LYW
 * @since 2022-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TopicVO implements Serializable {

    /**
     * 题目类型id
     */
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

    /**
     * cpsrcd子题列表
     */
    private List<CpsrcdVO> subCpsrcds;


}
