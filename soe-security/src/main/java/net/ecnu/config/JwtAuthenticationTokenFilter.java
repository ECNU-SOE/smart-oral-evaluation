package net.ecnu.config;

import com.alibaba.fastjson.JSON;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.model.JwtProperties;
import net.ecnu.model.common.LoginUser;
import net.ecnu.service.MyUserDetailsService;
import net.ecnu.util.JsonData;
import net.ecnu.utils.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * JWT令牌授权过滤器
 * 1、解析JWT令牌，获得令牌中payload的用户名（暂时用户名为手机号）
 * 2、根据用户名加载该用户的用户信息、角色信息、接口权限信息
 * 3、验证JWT令牌的有效性
 * 4、如果令牌有效，通过Spring Security对该用户及其接口访问进行授权
 * 5、没有被授权的访问请求，会被Spring Security拦截，返回403禁止访问
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private JwtProperties jwtProperties;
    private JwtTokenUtil jwtTokenUtil;
    private MyUserDetailsService myUserDetailsService;

    private JwtAuthenticationTokenFilter(){}

    public JwtAuthenticationTokenFilter(JwtProperties jwtProperties,
                                        JwtTokenUtil jwtTokenUtil,
                                        MyUserDetailsService myUserDetailsService) {
        this.jwtProperties = jwtProperties;
        this.jwtTokenUtil = jwtTokenUtil;
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String jwtToken = request.getHeader(jwtProperties.getHeader());
        if(!StringUtils.isBlank(jwtToken)){
            String username = null;
            LoginUser loginUser = jwtTokenUtil.checkJWT(jwtToken);
            if(Objects.isNull(loginUser)){//token校验失败处理
                tokenErrorHandler(response);
            }else{
                //将用户唯一ID放入请求头中
                request.setAttribute("accountNo",loginUser.getAccountNo());
                //用户名暂时只能是手机号，后期再做优化
                if(!Objects.isNull(loginUser)&&StringUtils.isNotBlank(loginUser.getPhone())){
                    username = loginUser.getPhone();
                }
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
                    //校验JWT令牌有效性及是否过期
                    if(jwtTokenUtil.validateToken(jwtToken)){
                        //给使用该JWT令牌的用户进行授权
                        UsernamePasswordAuthenticationToken authenticationToken
                                = new UsernamePasswordAuthenticationToken(
                                userDetails,null, userDetails.getAuthorities());
                        //Spring Security将可以访问的接口授权访问
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        }
        filterChain.doFilter(request,response);
    }

    private static void tokenErrorHandler(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(JsonData.buildCodeAndMsg(BizCodeEnum.TOKEN_EXCEPTION.getCode(),BizCodeEnum.TOKEN_EXCEPTION.getMessage())));
        out.flush();
        out.close();
    }
}
