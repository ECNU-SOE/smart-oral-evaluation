package net.ecnu.enums;

import lombok.Getter;

public enum BizCodeEnum {

    /**
     * 账号、权限相关
     */
    ACCOUNT_REPEAT(250001, "账号已经存在"),
    ACCOUNT_UNREGISTER(250002, "账号不存在"),
    ACCOUNT_PWD_ERROR(250003, "账号或者密码错误"),
    ACCOUNT_UNLOGIN(250004, "账号未登录"),
    UNAUTHORIZED_OPERATION(250005, "未授权的操作 或 资源不存在"),


    CLASS_REPEAT(25006,"班级已存在"),
    CLASS_UNEXISTS(250007, "班级不存在"),
    REPEAT_CHOOSE(250008,"重复选课"),
    USER_COURSE_UNEXISTS(250009,"该用户已退课"),

    /**
     * 语料组相关
     */
    CPSGRP_ERROR(260001, "语料组异常"),


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