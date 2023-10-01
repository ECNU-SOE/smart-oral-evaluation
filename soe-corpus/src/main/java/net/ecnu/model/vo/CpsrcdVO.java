package net.ecnu.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
     * 所属题型id
     */
    private String topicId;
    private String cpsgrpId;

    /**
     * cpsrcd次序
     */
    @JsonProperty("cNum")
    private Integer cNum;

    /**
     * 题目类型
     */
    private String type;

    /**
     * 评测模式
     */
    private Integer evalMode;

    /**
     * 语料难度
     */
    private Integer difficulty;

    /**
     * 子题分值
     */
    private Double score;

    /**
     * 是否启用拼音
     */
    private Boolean enablePinyin;

    /**
     * 语料内容拼音
     */
    private String pinyin;

    /**
     * 语料文本内容
     */
    private String refText;

    /**
     * 示范音频播放url
     */
    private String audioUrl;

    /**
     * 题目备注
     */
    private String description;

    /**
     * 题目标签
     */
    private List<String> tags;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date gmtModified;


}
