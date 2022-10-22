package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.constant.SOEConst;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserRegisterReq {

    /**
     * 手机号(不能为空)
     * TODO 可以自定义手机号注解
     * TODO json返回数据格式
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = SOEConst.PHONE_PATTERN)
    private String phone;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String pwd;


    /**
     * 短信验证码
     */
    @NotBlank(message = "code不能为空")
    private String code;

}
