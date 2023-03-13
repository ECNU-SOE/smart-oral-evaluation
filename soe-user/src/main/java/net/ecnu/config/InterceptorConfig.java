package net.ecnu.config;

import lombok.extern.slf4j.Slf4j;
import net.ecnu.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginInterceptor())
                //添加拦截的路径
                .addPathPatterns("/api/user/v1/info2")
                .addPathPatterns("/api/course/v1/create")
                .addPathPatterns("/api/course/v1/delete/{id}")
                .addPathPatterns("/api/course/v1/update")

                //排除不拦截
                .excludePathPatterns();
    }
}
