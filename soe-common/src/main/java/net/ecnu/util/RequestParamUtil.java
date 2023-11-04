package net.ecnu.util;

import com.alibaba.csp.sentinel.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class RequestParamUtil {
    public static final String ACCOUNT_NO = "accountNo";

    public static String currentAccountNo() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            log.info("获取的请求属性为空");
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String idStr = request.getAttribute(ACCOUNT_NO).toString();
        return idStr;
    }

    public static String getToken(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes srat = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = srat.getRequest();
        String token = request.getHeader("token");
        if (StringUtil.isBlank(token))
            throw new BizException(BizCodeEnum.ACCOUNT_UNLOGIN);
        return token;
    }



}
