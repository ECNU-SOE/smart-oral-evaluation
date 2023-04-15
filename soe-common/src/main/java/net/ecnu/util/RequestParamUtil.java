package net.ecnu.util;

import lombok.extern.slf4j.Slf4j;
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
}
