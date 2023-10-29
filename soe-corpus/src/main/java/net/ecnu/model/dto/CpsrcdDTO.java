package net.ecnu.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.ecnu.model.TagDO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 语料VO类
 * </p>
 *
 * @author LYW
 * @since 2023-09-29
 */
@Data
public class CpsrcdDTO implements Serializable {

    /**
     * 主键id
     */
    private String id;

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
     * 语料内容拼音
     */
    private String pinyin;

    /**
     * 语料文本内容
     */
    private String refText;

    /**
     * 文本对齐方式：-1左对齐、0居中、1右对齐、2两端对齐
     */
    private Integer textAlign;

    /**
     * 示范音频播放url
     */
    private String audioUrl;

    /**
     * 语料组使用频次
     */
    private Integer usedBy;

    /**
     * tags
     */
    private List<TagDO> tags;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date gmtModified;


}
