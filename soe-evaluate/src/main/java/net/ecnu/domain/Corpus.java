package net.ecnu.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Corpus {
    private String corpusId;
    private int type;
    private String pinyin;
    private String content;
    private String creator;
    private Date gmtCreate;
    private Date gmtModified;
}
