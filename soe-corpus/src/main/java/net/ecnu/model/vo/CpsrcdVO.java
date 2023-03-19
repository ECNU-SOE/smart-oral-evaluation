package net.ecnu.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class CpsrcdVO implements Serializable {

    /**
     * 主键id
     */
    private String id;

    /**
     * 关联语料组id
     */
    @JsonIgnore
    private String cpsgrpId;

    /**
     * 组内次序
     */
    private Integer order;

    /**
     * 语料类型
     */
    private Integer type;

    /**
     * 语料难度
     */
    private Integer level;

    /**
     * 本题分值
     */
    private BigDecimal weight;

    /**
     * 语料内容拼音
     */
    private String pinyin;

    /**
     * 语料文本内容
     */
    private String refText;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @JsonIgnore
    private Date gmtModified;


}
