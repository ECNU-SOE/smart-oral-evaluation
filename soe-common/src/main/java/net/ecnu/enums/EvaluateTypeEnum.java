package net.ecnu.enums;

/**
 * 评测方式枚举
 * **/
public enum EvaluateTypeEnum {

    XF_EVALUATE(0,"科大讯飞评测"),
    ZY_EVALUATE(1,"自研评测");

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

    EvaluateTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
