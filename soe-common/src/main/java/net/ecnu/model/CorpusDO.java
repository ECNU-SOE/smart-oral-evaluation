package net.ecnu.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 语料原型
 * </p>
 *
 * @author LYW
 * @since 2022-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("corpus")
public class CorpusDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 语料id
     */
    @TableId(value = "id", type = IdType.NONE)
    private String id;

    /**
     * 语料类型
     */
    private Integer type;

    /**
     * 难易程度
     */
    private Integer level;

    /**
     * 汉语拼音
     */
    private String pinyin;

    /**
     * 参考文本
     */
    private String refText;

    /**
     * 创建者id
     */
    private String creator;

    /**
     * 逻辑删除位
     */
    private Boolean del;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;


}
