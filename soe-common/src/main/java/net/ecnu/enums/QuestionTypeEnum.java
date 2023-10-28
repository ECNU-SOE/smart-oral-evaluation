package net.ecnu.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 题型枚举类
 * **/
public enum QuestionTypeEnum {


    READ_WORDS(1,"朗读字词",2,"read_word"),
    READ_SENTENCE(2,"朗读句子",3,"read_sentence"),
    READ_POETRY(3,"朗读诗词",3,"read_sentence"),
    READ_ARTICLE(4,"朗读文章",4,"read_chapter"),
    SINGLE_CHOICE(5,"单选题",5,"answer_based"),
    MULTIPLE_CHOICE(6,"多选题",5,"answer_based"),
    WRITE_CHINESE_CHARACTERS(7,"写汉字",5,"answer_based"),
    ANSWER_BY_WATCH_VIDEO(8,"看视频答题",6,"open_ended"),
    OTHERS(9,"其他类型",6,"open_ended");

    /**
     * 题型Code
     * **/
    private Integer code;

    /**
     * 题型名称
     * **/
    private String msg;

    /**
     * 评测模式Code
     * **/
    private Integer modelCode;

    /**
     * 评测名称
     * **/
    private String modelMsg;

    QuestionTypeEnum(Integer code, String msg, Integer modelCode, String modelMsg) {
        this.code = code;
        this.msg = msg;
        this.modelCode = modelCode;
        this.modelMsg = modelMsg;
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

    public Integer getModelCode() {
        return modelCode;
    }

    public void setModelCode(Integer modelCode) {
        this.modelCode = modelCode;
    }

    public String getModelMsg() {
        return modelMsg;
    }

    public void setModelMsg(String modelMsg) {
        this.modelMsg = modelMsg;
    }
    /**
     * 根据题型Code，获取对应枚举信息
     * **/
    public static QuestionTypeEnum getEnumObjectByCode(Integer code) {
        for (QuestionTypeEnum value : QuestionTypeEnum.values()) {
            if(value.getCode().intValue() == code.intValue()){
                return value;
            }
        }
        return null;
    }

    /**
     * 根据评测模式Code，获取同评测模式题型枚举对象
     * **/
    private static List<QuestionTypeEnum> getSameModeTypeEnumByModelCode(Integer modelCode){
        List<QuestionTypeEnum> list = new ArrayList<>();
        for (QuestionTypeEnum value : QuestionTypeEnum.values()) {
            if(value.getModelCode().intValue() == modelCode.intValue()){
                list.add(value);
            }
        }
        return list;
    }
}
