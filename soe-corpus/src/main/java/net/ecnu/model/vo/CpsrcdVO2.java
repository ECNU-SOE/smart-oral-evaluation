package net.ecnu.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * corpus快照2
 * </p>
 *
 * @author TGX
 * @since 2022-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CpsrcdVO2 implements Serializable {

    /**
     * 主键id
     */
    private String id;

    /**
     * 组内次序
     */
    private Integer order;

    /**
     * 语料难度
     */
    private Integer level;

    /**
     * 语料内容拼音
     */
    private String pinyin;

    /**
     * 语料文本内容
     */
    private String refText;



}
