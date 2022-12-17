package net.ecnu.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @Author 刘少煜
 * @Date 2022/12/13 23:29
 */
@Configuration        //作为配置类，加入Bean容器中
@EnableSwagger2        //swagger注解
public class Swagger2Config {
    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                //.paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                //.paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build()
                .globalOperationParameters(getParameterList());
    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder().title("soe-user服务API文档")
                .description("本文档描述了汉语正音平台soe-user服务的接口定义")
                .version("1.0")
                .contact(new Contact("刘少煜", "https://github.com/ECNU-SOE/smart-oral-evaluation/tree/branch-authentication",
                        "1765947424@qq.com"))
                .build();
    }

    private List<Parameter> getParameterList(){
        ParameterBuilder token = new ParameterBuilder();
        List<Parameter> param = new ArrayList<>();
        token.name("token")
                .description("令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        param.add(token.build());
        return param;
    }

}
