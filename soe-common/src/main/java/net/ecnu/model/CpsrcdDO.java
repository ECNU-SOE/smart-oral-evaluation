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
 * corpus快照
 * </p>
 *
 * @author LYW
 * @since 2023-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("cpsrcd")
public class CpsrcdDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
      @TableId(value = "id", type = IdType.AUTO)
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
    private Integer cNum;

    /**
     * 评测模式
     */
    private Integer evalMode;

    /**
     * 题目类型
     */
    private String type;

    /**
     * 语料难度
     */
    private Integer difficulty;

    /**
     * 每字分值❌
     */
    private BigDecimal wordWeight;

    /**
     * 本题分值
     */
    private BigDecimal score;

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
     * 题目标签
     */
    private String tags;

    /**
     * 创建时间❌
     */
    private Date gmtCreate;

    /**
     * 更新时间❌
     */
    private Date gmtModified;


}
