package net.ecnu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 */
//@Configuration//功能：将想要的组件添加到容器中
public class CorsConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // 所有接口
//                .allowCredentials(true) // 是否发送 Cookie
//                .allowedOriginPatterns("*") // 支持域
//                .allowedMethods("GET", "POST", "PUT", "DELETE") // 支持方法
//                .allowedHeaders("*")
//                .exposedHeaders("*");
//    }
}

