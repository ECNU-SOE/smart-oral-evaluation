package net.ecnu.enums;

import net.ecnu.exception.BizException;

import java.util.Objects;

/**
 * @description:用户-课程 关系表user_class的角色枚举类
 * @Author lsy
 * @Date 2023/7/31 0:08
 */
public enum ClassRoleTypeEnum {


    STUDENT(1,"学生"),
    ASSISTANT(2,"助教"),
    TEACHER(3,"老师"),
    MANAGER(4,"管理员")
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

    ClassRoleTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(Integer code) {
        if (Objects.isNull(code)) {
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY);
        }
        for (ClassRoleTypeEnum value : ClassRoleTypeEnum.values()) {
            if (value.getCode().intValue() == code.intValue()) {
                return value.getMsg();
            }
        }
        return "";
    }
}
