package net.ecnu.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
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


}
