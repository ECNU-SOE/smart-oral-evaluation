package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.constant.SOEConst;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Find;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserReq {

    /**
     * 昵称
     */
    @NotBlank(message = "nickName can't be empty in create", groups = {Create.class})
    private String nickName;

    /**
     * 手机号(不能为空)
     * TODO 可以自定义手机号注解
     * TODO json返回数据格式
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = SOEConst.PHONE_PATTERN, groups = {Create.class, Find.class})
    private String phone;

    /**
     * 密码
     * 长度应该在8～15位之间
     */
    @Size(min = 8, max = 15, message = "The password must contain 8 to 15 characters", groups = {Create.class, Find.class})
//    @NotBlank(message = "密码不能为空", groups = {Create.class, Find.class})
    private String pwd;


//    /**
//     * 短信验证码
//     */
//    @NotBlank(message = "code不能为空", groups = {Create.class})
//    private String code;

}
