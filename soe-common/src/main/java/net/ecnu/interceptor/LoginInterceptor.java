package net.ecnu.interceptor;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.model.LoginUser;
import net.ecnu.util.CommonUtil;
import net.ecnu.util.JWTUtil;
import net.ecnu.util.JsonData;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    public static ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler)
            throws Exception {
        if (HttpMethod.OPTIONS.toString().equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }
        //获取accessToken
        String accessToken = request.getHeader("token");
        if (StringUtils.isBlank(accessToken)) {
            accessToken = request.getParameter("token");
        }

        if (StringUtils.isNotBlank(accessToken)) {
            LoginUser loginUser = JWTUtil.checkJWT(accessToken);
            if (loginUser == null) {// 未登录 or token过期
                CommonUtil.sendJsonMessage(response, JsonData.buildResult(BizCodeEnum.ACCOUNT_UNLOGIN));
                return false;
            }

            //通过Attribute
            //request.setAttribute("loginUser",loginUser);

            //通过threadLocal
            threadLocal.set(loginUser);
            return true;
        }
        CommonUtil.sendJsonMessage(response, JsonData.buildResult(BizCodeEnum.ACCOUNT_UNLOGIN));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        threadLocal.remove();
    }
}
