package net.ecnu.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:学生答题报告数据实体类
 * @Author lsy
 * @Date 2023/9/10 16:25
 */
@Data
public class StudentTranscriptDto {

    /**
     * 语料id
     * **/
    private String cpsgrpId;

    /**
     * 学生的唯一id
     * **/
    private String respondent;

    /**
     * 建议得分
     * **/
    private BigDecimal suggestedScore;

    /**
     * 答题时间
     * **/
    private Date gmtCreate;
}
