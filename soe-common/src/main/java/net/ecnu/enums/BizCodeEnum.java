package net.ecnu.enums;

import lombok.Getter;

public enum BizCodeEnum {

    /**
     * 账号
     */
    ACCOUNT_REPEAT(250001, "账号已经存在"),
    ACCOUNT_UNREGISTER(250002, "账号不存在"),
    ACCOUNT_PWD_ERROR(250003, "账号或者密码错误"),
    ACCOUNT_UNLOGIN(250004, "账号未登录"),

    ;
    @Getter
    private final int code;

    @Getter
    private final String message;

    BizCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}