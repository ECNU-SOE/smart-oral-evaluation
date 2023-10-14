package net.ecnu.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Phone {

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
     * 时长（单位：帧，每帧相当于10ms）
     * **/
    @JsonProperty("time_len")
    private int timeLen;

    /**
     * 错误信息：1(声韵错）2(调型错）3(声韵调型错），当dp_message不为0时，
     * perr_msg可能出现与dp_message值保持一致的情况
     * **/
    @JsonProperty("perr_msg")
    private int perrMsg;
}