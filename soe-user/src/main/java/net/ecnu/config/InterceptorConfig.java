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
                .addPathPatterns("/api/user/v1/info")
                .addPathPatterns("/api/course/v1/add")
                .addPathPatterns("/api/course/v1/del/{id}")
                .addPathPatterns("/api/course/v1/update")
                .addPathPatterns("/api/course/v1/list_user_cour")
                .addPathPatterns("/api/course/v1/add_user_cour")
                .addPathPatterns("/api/course/v1/del_user_cour/{id}")
                .addPathPatterns("/api/course/v1/add_test")
                .addPathPatterns("/api/course/v1/update_test")
                .addPathPatterns("/api/course/v1/del_test/{id}")
                .addPathPatterns("/api/user/v1/accno")
                //排除不拦截
                .excludePathPatterns();
    }
}
