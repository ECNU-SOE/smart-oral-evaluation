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
    TOKEN_EXCEPTION(250006, "用户登录信息异常或已失效"),
    USER_PHONE_FORMAT_ERROR(250007, "仅支持中国大陆手机号，改手机号格式有误"),
    USER_INPUT_ERROR(250008, "您输入的数据格式错误或您没有权限访问资源"),

    /**
     * 课程班级模块
     */
    CLASS_REPEAT(25006, "班级已存在"),
    CLASS_UNEXISTS(250007, "班级不存在"),
    REPEAT_CHOOSE(250008, "重复选课"),
    USER_COURSE_UNEXISTS(250009, "选课信息不存在"),
    COURSE_UNEXISTS(250010, "课程不存在"),
    COURSE_REPEAT(250011, "课程重复"),
    COURSE_USING(250012, "该课程被班级关联"),

    /**
     * 语料组相关
     */
    CPSGRP_ERROR(260001, "语料组异常"),
    CPSGRP_NOT_EXIST(260002, "语料组不存在"),

    /**
     * 语料相关
     */
    CORPUS_DEL_ERROR(270001, "删除语料异常"),
    CORPUS_UPDATE_ERROR(270002, "更新语料异常"),
    CORPUS_ADD_ERROR(270003, "添加语料异常"),

    /**
     * 参数异常
     */
    PARAM_CANNOT_BE_EMPTY(350000, "参数不能为空");
    @Getter
    private final int code;

    @Getter
    private final String message;

    BizCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}