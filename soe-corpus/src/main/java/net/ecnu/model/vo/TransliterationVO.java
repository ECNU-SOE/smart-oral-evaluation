package net.ecnu.model.vo;

import lombok.Data;

/**
 * 音译信息VO类
 * **/
@Data
public class TransliterationVO {

    /**
     * 音译id
     * **/
    private Integer id;

    /**
     * 音译音频OSS地址
     * **/
    private String audioUrl;

    /**
     * 音译文本
     * **/
    private String audioText;

}
