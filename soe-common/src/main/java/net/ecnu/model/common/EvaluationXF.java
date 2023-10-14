package net.ecnu.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @description:科大讯飞响应出参
 * @Author lsy
 * @Date 2023/10/14 15:46
 */
@Data
public class EvaluationXF {

    /**
     * 语言
     * **/
    private String lan;

    /**
     * 类型
     * **/
    private String type;

    /**
     *
     * **/
    private String version;

    @JsonProperty("rec_paper")
    private RecPaper recPaper;
}
