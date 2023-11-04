package net.ecnu.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 题型枚举类
 * **/
public enum QuestionTypeEnum {

    READ_WORDS(1,"朗读字词"),
    READ_SENTENCE(2,"朗读句子"),
    READ_POETRY(3,"朗读诗词"),
    READ_ARTICLE(4,"朗读文章"),
    SINGLE_CHOICE(5,"单选题"),
    MULTIPLE_CHOICE(6,"多选题"),
    WRITE_CHINESE_CHARACTERS(7,"写汉字"),
    ANSWER_BY_WATCH_VIDEO(8,"看视频答题"),
    OTHERS(9,"其他类型");

    /**
     * 题型Code
     * **/
    private Integer code;

    /**
     * 题型名称
     * **/
    private String msg;



    QuestionTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 根据题型Code，获取对应枚举信息
     * **/
    public static String getMsgByCode(Integer code) {
        for (QuestionTypeEnum value : QuestionTypeEnum.values()) {
            if(value.getCode().intValue() == code.intValue()){
                return value.getMsg();
            }
        }
        return null;
    }

    /**
     * 根据题型名称来获取Code
     * **/
    public static Integer getCodeByMsg(String questionMsg) {
        for (QuestionTypeEnum value : QuestionTypeEnum.values()) {
            if(value.getMsg().equals(questionMsg)){
                return value.getCode();
            }
        }
        return null;
    }
}
