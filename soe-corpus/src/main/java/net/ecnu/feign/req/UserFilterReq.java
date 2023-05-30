package net.ecnu.feign.req;

import lombok.Data;

import java.util.Date;

@Data
public class UserFilterReq {

    /**
     * 用户账号id
     */
    private String accountNo;

    /**
     * 身份认证id
     */
    private String identifyId;

    /**
     * 用户角色
     */
    private Integer roleId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 用户母语
     */
    private Integer firstLanguage;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 生日
     */
    private Date birth;

    /**
     * 手机
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;
}
