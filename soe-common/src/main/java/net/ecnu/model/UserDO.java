package net.ecnu.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author LYW
 * @since 2022-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class UserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户账号id
     */
    @TableId(value = "account_no", type = IdType.NONE)
    private String accountNo;

    /**
     * 身份认证id
     */
    private String identifyId;


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
     * 个性签名
     */
    private String sign;

    /**
     * 手机
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 加密盐
     */
    private String secret;

    /**
     * 删除标识位
     */
    private Boolean del;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;

    /**
     * 0无效用户，1有效用户
     */
    private Boolean enabled;

    /**
     * 用户所属组织id
     */
    private Integer orgId;

}