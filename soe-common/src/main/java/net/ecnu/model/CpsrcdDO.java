package net.ecnu.model;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("cpsrcd")
public class CpsrcdDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.NONE)
    private String id;

    /**
     * 关联语料组id
     */
    private String cpsgrpId;

    /**
     * 组内次序
     */
    @TableField(value = "`order`")
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
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;


}
