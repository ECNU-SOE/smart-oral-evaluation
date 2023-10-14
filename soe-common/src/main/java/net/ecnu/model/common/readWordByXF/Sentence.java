package net.ecnu.model.common.readWordByXF;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @description:
 * @Author lsy
 * @Date 2023/10/14 16:16
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
     * **/
    @JsonProperty("end_pos")
    private int endPos;

    /**
     * 时长
     * 单位：帧，每帧相当于10ms
     * **/
    @JsonProperty("time_len")
    private int timeLen;

    private Word word;
}
