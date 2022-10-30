package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.constant.SOEConst;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Find;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserReq {

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
     */
    @NotBlank(message = "密码不能为空", groups = {Create.class, Find.class})
    private String pwd;


    /**
     * 短信验证码
     */
    @NotBlank(message = "code不能为空", groups = {Create.class})
    private String code;

}
