package net.ecnu.model.common.readSentenceByXF;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.ecnu.model.common.Word;

import java.util.List;

/**
 * @description:
 * @Author lsy
 * @Date 2023/10/14 23:23
 */
@Data
public class Sentence {

    /**
     * 开始位置
     * **/
    @JsonProperty("beg_pos")
    private int begPos;

    /**
     * 内容
     * **/
    private String content;

    /**
     * 结束位置
     * 单位:帧，每帧相当于10ms
     * **/
    @JsonProperty("end_pos")
    private int endPos;

    /**
     * 流畅度分（讯飞文档注释：暂会返回0分）
     * **/
    @JsonProperty("fluency_score")
    private int fluencyScore;

    /**
     * 声韵分
     * **/
    @JsonProperty("phone_score")
    private double phoneScore;

    /**
     * 时长
     * **/
    @JsonProperty("time_len")
    private int timeLen;

    /**
     * 调型分
     * **/
    @JsonProperty("tone_score")
    private double toneScore;

    /**
     * 总分
     * **/
    @JsonProperty("total_score")
    private double totalScore;


    private List<Word> word;
}
