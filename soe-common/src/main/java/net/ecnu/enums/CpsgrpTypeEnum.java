package net.ecnu.enums;

import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @Author lsy
 * @Date 2023/8/20 11:12
 */
public enum CpsgrpTypeEnum {

    TEXT(1,"测验"),
    EXAM(2,"考试");

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

    CpsgrpTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static List<CpsgrpTypeEnum> getAllTypeInfo(){
        return Arrays.asList(CpsgrpTypeEnum.values());
    }
}
