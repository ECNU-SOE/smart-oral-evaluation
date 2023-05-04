package net.ecnu.aop;

import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.model.common.LoginUser;
import net.ecnu.util.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @description:Token解析切面，临时使用，权限认证引入语料服务后删除
 * @Author lsy
 * @Date 2023/5/3 21:52
 */
@Service
@Aspect
public class TokenAspect {

    @Pointcut("@annotation(net.ecnu.aop.annotate.TokenAnalysis)")
    private void tokenCheck() {
    }

    @Around("tokenCheck()")
    public Object tokenCheckFirst(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取HTTP请求
        HttpServletRequest request = attributes.getRequest();
        String jwtToken = request.getHeader("token");
        if(!StringUtils.isBlank(jwtToken)){
            LoginUser loginUser = JWTUtil.checkJWT(jwtToken);
            if(!Objects.isNull(loginUser)){
                request.setAttribute("accountNo",loginUser.getAccountNo());
            }else{
                throw new BizException(BizCodeEnum.TOKEN_EXCEPTION.getCode(),"token解析异常");
            }
        }else{
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION.getCode(),"token为空");
        }
        return joinPoint.proceed();
    }
}
