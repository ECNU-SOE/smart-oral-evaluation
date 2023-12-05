package net.ecnu.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Joshua
 * @description
 * @date 2023/12/5 22:39
 */
public enum CategoryEnum {

    READ_SYLLABLE(0,"read_syllable"),
    READ_WORD(1,"read_word"),
    READ_SENTENCE(2,"read_sentence"),
    READ_CHAPTER(3,"read_chapter")
    ;

    private Integer code;

    private String msg;

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

    CategoryEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static List<Map<String,String>> getAllTypeInfo(){
        List<Map<String,String>> resultList = new ArrayList<>();
        for (CategoryEnum value : CategoryEnum.values()) {
            Map<String,String> map = new HashMap<>();
            map.put("code",String.valueOf(value.getCode()));
            map.put("msg",value.getMsg());
            resultList.add(map);
        }
        return resultList;
    }
}
