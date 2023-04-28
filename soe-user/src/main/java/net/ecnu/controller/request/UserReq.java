package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.constant.SOEConst;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Find;
import net.ecnu.controller.group.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class UserReq {

    private String accountNo;
//    private String identifyId;
    private String nickName;
    private String realName;
    private String avatarUrl;
    //用户母语
    private Integer firstLanguage;
    private Integer sex;
    private Date birth;
    //个性签名
    private String sign;

    /**
     * 手机号(不能为空)
     * TODO 可以自定义手机号注解
     * TODO json返回数据格式
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = SOEConst.PHONE_PATTERN, groups = {Create.class, Find.class})
    private String phone;
    private String mail;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = {Create.class, Find.class})
    private String pwd;

//    private Boolean enabled;
//    private Integer orgId;

    /**
     * 短信验证码
     */
    @NotBlank(message = "code不能为空", groups = {Create.class})
    private String code;

}
