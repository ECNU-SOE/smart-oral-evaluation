package net.ecnu.service;

import net.ecnu.constant.RolesConst;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.mapper.MyUserDetailsServiceMapper;
import net.ecnu.model.UserDO;
import net.ecnu.model.UserReq;
import net.ecnu.model.common.LoginUser;
import net.ecnu.util.IDUtil;
import net.ecnu.util.JsonData;
import net.ecnu.utils.JwtTokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.Objects;

public class JwtAuthService {
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private MyUserDetailsServiceMapper myUserDetailsServiceMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    private JwtAuthService(){}

    public JwtAuthService(AuthenticationManager authenticationManager,
                          JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * 登录认证换取JWT令牌
     */
    public String login(String username, String password){
        LoginUser loginUser = new LoginUser();
        try {
            UsernamePasswordAuthenticationToken upToken =
                    new UsernamePasswordAuthenticationToken(username,password);
            Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDO userInfoByPhone = myUserDetailsServiceMapper.findUserInfoByPhone(username);
            BeanUtils.copyProperties(userInfoByPhone,loginUser);
        } catch (AuthenticationException e) {
            throw new BizException(BizCodeEnum.USER_INPUT_ERROR.getCode(),BizCodeEnum.USER_INPUT_ERROR.getMessage());
        }
        return jwtTokenUtil.geneJsonWebToken(loginUser);
    }

    /**
     * 刷新token令牌
     */
    public String refreshToken(String token){
        //获取token中的LoginUser对象
        LoginUser loginUser = jwtTokenUtil.checkJWT(token);
        if(Objects.isNull(loginUser)){
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION.getCode(),BizCodeEnum.TOKEN_EXCEPTION.getMessage());
        }
        return jwtTokenUtil.geneJsonWebToken(loginUser);
    }

    /**
     * 用户注册，无需返回token令牌
     */
    public JsonData register(UserReq userReq){
        /**
         * 短信认证
         * TODO
         */
        //查询系统中是否已存在手机号相同的账户
        Integer isExists = myUserDetailsServiceMapper.checkUserByPhone(userReq.getPhone());
        if(isExists>0){
            return JsonData.buildCodeAndMsg(BizCodeEnum.ACCOUNT_REPEAT.getCode(),BizCodeEnum.ACCOUNT_REPEAT.getMessage());
        }else{
            //新增用户入库，客户端新增用户默认角色为学生
            UserDO newUserDO = new UserDO();
            //雪花算法生成用户唯一识别码
            String accountNo = "user_" + IDUtil.getSnowflakeId();
            newUserDO.setAccountNo(accountNo);
            newUserDO.setPhone(userReq.getPhone());
            newUserDO.setPwd(passwordEncoder.encode(userReq.getPwd()));
            //新增用户进user表
            myUserDetailsServiceMapper.insert(newUserDO);
            //给新增用户赋予默认角色
            myUserDetailsServiceMapper.insertUserRole(RolesConst.DEFAULT_ROLE,accountNo);
            return JsonData.buildSuccess("注册成功");
        }
    }
}
