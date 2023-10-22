package net.ecnu.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 音译表
 */
@Data
public class Transliteration implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 音频oss地址
     */
    private String audioUrl;

    /**
     * 音译文本内容
     */
    private String audioText;

    /**
     * 逻辑删除位
     */
    private Boolean delFlg;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}