package net.ecnu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Joshua
 * @description 后台管理系统 - 成绩单管理 - 查询返回类
 * @date 2023/12/10 13:42
 */
@Data
public class TranscriptInfoResp {

    /**
     * 答题报告id
     * **/
    private String transcriptId;

    /**
     * 语料组名称
     * **/
    private String cpsgrpName;

    /**
     * 难度
     * **/
    private String difficulty;

    /**
     * 答题人Code
     * **/
    private String respondentCode;

    /**
     * 答题人
     * **/
    private String respondentName;

    /**
     * 提交时间
     * **/
    @JsonFormat(locale = "zh",timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date commitTime;

    /**
     * 系统评分
     * **/
    private BigDecimal systemScore;

    /**
     * 批阅得分
     * **/
    private BigDecimal manualScore;

    /**
     * 批阅人Code
     * **/
    private String reviewerCode;

    /**
     * 批阅人
     * **/
    private String reviewerName;

    /**
     * 批阅时间
     * **/
    @JsonFormat(locale = "zh",timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reviewTime;

}
