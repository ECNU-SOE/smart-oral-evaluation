package net.ecnu.model.common.readSyllableByXF;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.ecnu.model.common.Word;

/**
 * @description:
 * @Author lsy
 * @Date 2023/10/14 23:06
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
