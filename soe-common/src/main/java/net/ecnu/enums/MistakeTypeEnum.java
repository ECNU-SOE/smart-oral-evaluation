package net.ecnu.enums;

/**
 * @description:
 * @Author lsy
 * @Date 2023/7/15 16:31
 */
public enum MistakeTypeEnum {

    DEFAULT(0,"语音评测")
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

    MistakeTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(Integer code) {
        for (MistakeTypeEnum value : MistakeTypeEnum.values()) {
            if(value.getCode().intValue() == code.intValue()){
                return value.getMsg();
            }
        }
        return "";
    }
}
