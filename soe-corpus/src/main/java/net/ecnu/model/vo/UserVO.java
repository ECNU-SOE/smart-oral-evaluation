package net.ecnu.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * user快照
 * </p>
 *
 * @author TGX
 * @since 2022-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserVO implements Serializable {

    /**
     * 用户账号id
     */
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
     * 用户头像url
     */
    private String avatarUrl;

    /**
     * 用户母语
     */
    private String firstLanguage;

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

//        /**
//         * 密码
//         */
//        private String pwd;

//        /**
//         * 加密盐
//         */
//        private String secret;

//        /**
//         * 删除标识位
//         */
//        private Boolean del;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;

    /**
     * 用户是否被禁用 1-激活用户  0-禁用用户
     */
    private Boolean enabled;

    /**
     * 所属组织id
     */
    private Integer orgId;
}
