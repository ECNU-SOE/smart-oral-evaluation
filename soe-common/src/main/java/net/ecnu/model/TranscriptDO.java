package net.ecnu.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @description 
 * @author Joshua
 * @date 2023/12/10 17:22
 */

/**
 * 成绩单
 */
@Data
public class TranscriptDO implements Serializable {
    /**
     * 评测报告id
     */
    private String id;

    /**
     * 关联语料组id
     */
    private String cpsgrpId;

    /**
     * 答题人id
     */
    private String respondent;

    /**
     * 系统评测得分
     */
    private BigDecimal systemScore;

    /**
     * 人工评测得分
     */
    private BigDecimal manualScore;

    /**
     * 批阅时间
     * **/
    private Date markTime;

    /**
     * 批阅人Code（user.account_no）
     * **/
    private String markUser;

    /**
     * json格式报告
     */
    private String resJson;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;

    private static final long serialVersionUID = 1L;
}