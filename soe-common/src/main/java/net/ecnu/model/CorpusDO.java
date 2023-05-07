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
 * @author TGX
 * @since 2023-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("corpus")
public class CorpusDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 语料id
     */
    @TableId(value = "id")
    private String id;

    /**
     * 汉语拼音
     */
    private String pinyin;

    /**
     * 参考文本
     */
    private String refText;

    /**
     * 评测模式：1字、2词、3句、4章
     */
    private Integer evalMode;

    /**
     * 难易程度
     */
    private Integer difficulty;

    /**
     * 每字分值
     */
    private Double wordWeight;

    /**
     * 示范音频
     * */
    private String audioUrl;

    /**
     * 标签
     * */
    private String tags;

    /**
     * 创建者id
     */
    private String creator;

    /**
     * 逻辑删除标识位
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
