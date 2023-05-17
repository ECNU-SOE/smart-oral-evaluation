package net.ecnu.controller.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import net.ecnu.constant.SOEConst;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Find;
import net.ecnu.controller.group.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;
import javax.validation.constraints.Size;

@Data
public class UserReq {
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
//    @NotBlank(message = "nickName can't be empty in create", groups = {Create.class})
    private String nickName;

    /**
     * 真实姓名
     */
    @NotBlank(message = "realName can't be empty in create", groups = {Create.class})
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
    @NotBlank(message = "手机号不能为空", groups = {Create.class})
//    @Pattern(regexp = SOEConst.PHONE_PATTERN, groups = {Create.class, Find.class})
    private String phone;

    /**
     * 邮箱
     */
    private String mail;


    /**
     * 密码
     * 长度应该在8～15位之间
     */
    @Size(min = 8, max = 15, message = "The password must contain 8 to 15 characters", groups = {Create.class})
//    @NotBlank(message = "密码不能为空", groups = {Create.class, Find.class})
    private String pwd;

//    /**
//     * 加密盐
//     */
//    private String secret;

//    /**
//     * 删除标识位
//     */
//    private Boolean del;

//    /**
//     * 创建时间
//     */
//    private Date gmtCreate;
//
//    /**
//     * 更新时间
//     */
//    private Date gmtModified;

    /**
     * 用户是否被禁用 1-激活用户  0-禁用用户
     */
    private Boolean enabled;

    /**
     * 所属组织id
     */
    private Integer orgId;

}
