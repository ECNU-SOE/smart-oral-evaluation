package net.ecnu.controller;

import net.ecnu.constant.SOEConst;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.model.JwtProperties;
import net.ecnu.model.UserReq;
import net.ecnu.model.common.LoginUser;
import net.ecnu.service.JwtAuthService;
import net.ecnu.util.JsonData;
import net.ecnu.utils.JWTConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

@RestController
@ConditionalOnBean({JwtAuthService.class})
@ConditionalOnProperty(name = "soe.jwt.useDefaultController",havingValue = "true")
public class JwtAuthController {

    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private JwtAuthService jwtAuthService;

    /**
     * 用户登录统一接口
     * @param userReq
     * @return 返回该账号对应的token值
     */
    @PostMapping(JWTConstants.LOGIN)
    public JsonData login(@RequestBody UserReq userReq){
        String username = userReq.getPhone();
        String password = userReq.getPwd();
        if(StringUtils.isBlank(username)||StringUtils.isBlank(password)){
            return JsonData.buildCodeAndMsg(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(),BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getMessage());
        }
        return JsonData.buildSuccess(jwtAuthService.login(username,password));
    }

    /**
     * 支持用户在登录后定时刷新JWT令牌
     */
    @RequestMapping(JWTConstants.REFRESH_TOKEN)
    public JsonData refresh(@RequestHeader("${soe.jwt.header}")String token){
        try{
            String newToken = jwtAuthService.refreshToken(token);
            return JsonData.buildSuccess(newToken);
        }catch (BizException e){
            return JsonData.buildError(e.getMsg());
        }
    }

    /**
     * 用户注册接口,无需返回token，只有登录成功、token刷新时才返回token给前端
     */
    @PostMapping(JWTConstants.REGISTER)
    public JsonData register(@RequestBody @Validated UserReq userReq){
        //非空判断，暂时不做短信验证码的非空校验
        if(Objects.isNull(userReq)||StringUtils.isBlank(userReq.getPhone())||
            StringUtils.isBlank(userReq.getPwd())){
            return JsonData.buildCodeAndMsg(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(),BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getMessage());
        }
        /**
         * 手机号校验，判断是否符合手机号的格式
         */
        if(!userReq.getPhone().matches(SOEConst.PHONE_PATTERN)){
            return JsonData.buildCodeAndMsg(BizCodeEnum.USER_PHONE_FORMAT_ERROR.getCode(),BizCodeEnum.USER_PHONE_FORMAT_ERROR.getMessage());
        }
        return jwtAuthService.register(userReq);
    }


}
