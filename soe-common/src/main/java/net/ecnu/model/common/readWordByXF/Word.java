package net.ecnu.model.common.readWordByXF;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Word {

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
     * **/
    @JsonProperty("end_pos")
    private int endPos;

    /**
     * 拼音: 数字代表声调，5表示轻声
     * **/
    private String symbol;

    /**
     * 时长（单位:帧，每帧相当于10ms）
     * **/
    @JsonProperty("time_len")
    private int timeLen;

    private List<Syll> syll;
}