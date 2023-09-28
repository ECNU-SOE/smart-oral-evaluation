package net.ecnu.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
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
     * 语料原型id
     */
    private String corpusId;

    /**
     * 所属语料组id
     */
    private String cpsgrpId;

    /**
     * 所属大题id
     */
    private String topicId;

    /**
     * cpsrcd次序
     */
    @JsonProperty("cNum")
    private Integer cNum;

    /**
     * 评测模式
     */
    private Integer evalMode;

    /**
     * 语料难度
     */
    private Integer difficulty;

    /**
     * 每字分值
     */
    private BigDecimal wordWeight;


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
     * 题目标签
     */
    private List<String> tags;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "YYYY-MM-DD hh:mm:ss")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "YYYY-MM-DD hh:mm:ss")
    private Date gmtModified;


}
