package net.ecnu.config;

import lombok.extern.slf4j.Slf4j;
//import net.ecnu.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*
@Configuration
@Slf4j
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginInterceptor())
                //添加拦截的路径
                .addPathPatterns(
                        "/api/corpus/v1/add",
                        "/api/file/v1/upload",
                        "/api/cpsgrp/v1/create", "/api/cpsgrp/v1/del", "/api/cpsgrp/v1/save_transcript", "/api/cpsgrp/v1/transcripts",
                        "/api/cpsgrp/v1/transcript")

                //排除不拦截
                .excludePathPatterns();
    }
}*/
