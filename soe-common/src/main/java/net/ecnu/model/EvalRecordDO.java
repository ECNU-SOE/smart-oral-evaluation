package net.ecnu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author LYW
 * @since 2023-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("eval_record")
public class EvalRecordDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评测记录id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 音频id
     */
    private String audioId;

    /**
     * 评测题目id
     */
    private String cpsrcdId;

    /**
     * 人工打分json结果
     */
    private String manRes;

    /**
     * 算法打分json结果
     */
    private String algRes;

    /**
     * 人工打分者
     */
    private String grader;

    /**
     * 评测创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;


}
