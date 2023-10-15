package net.ecnu.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @description:
 * @Author lsy
 * @Date 2023/10/14 16:31
 */
@Data
public class Syll {

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
     * 增漏信息，0(正确）16(漏读）32(增读）64(回读）128(替换）
     * **/
    @JsonProperty("dp_message")
    private int dpMessage;

    /**
     * 结束位置
     * **/
    @JsonProperty("end_pos")
    private int endPos;

    /**
     * paper(试卷内容）,sil(非试卷内容）
     * **/
    @JsonProperty("rec_node_type")
    private String recNodeType;

    /**
     * 时长（单位：帧，每帧10ms）
     * **/
    @JsonProperty("time_len")
    private int timeLen;

    private Phone phone;
}
