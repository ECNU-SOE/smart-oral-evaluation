package net.ecnu.config;

import net.ecnu.model.JwtProperties;
import net.ecnu.service.JwtAuthService;
import net.ecnu.service.MyUserDetailsService;
import net.ecnu.utils.JWTConstants;
import net.ecnu.utils.JwtTokenUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;

@Configuration
@Order(1)
public class JwtWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private MyUserDetailsService myUserDetailsService;

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    private String[] PERMIT_API_LIST = {
            //用户登录
            JWTConstants.LOGIN,
            //端上用户登录
            JWTConstants.APP_LOGIN,
            //端上用户注册
            JWTConstants.APP_REGISTER,
            //注册
            JWTConstants.REGISTER,
            //刷新token
            JWTConstants.REFRESH_TOKEN
    };

    private String[] PERMIT_RES_LIST = {
            "/swagger-ui.html",
            "/v2/api-docs", // swagger api json
            "/swagger-resources/configuration/ui", // 用来获取支持的动作
            "/swagger-resources", // 用来获取api-docs的URI
            "/swagger-resources/configuration/security", // 安全选项
            "/swagger-resources/**",
            //补充路径，近期在搭建swagger接口文档时，通过浏览器控制台发现该/webjars路径下的文件被拦截，故加上此过滤条件即可。
            "/webjars/**"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        if(jwtProperties.getCsrfDisabled()){
            http = http.csrf().disable();//禁用跨站csrf攻击防御
        }
        http.cors()
            .and().addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
                .antMatchers(PERMIT_API_LIST).permitAll();//允许指定接口无需走权限认证模块

        //通过配置实现的不需要JWT令牌就可以访问的接口
        for(String uri : jwtProperties.getPermitAllURI()){
            http.authorizeRequests().antMatchers(uri).permitAll();
        }
        /**
         * RBAC权限控制级别的接口权限校验
         */
        http.authorizeRequests().anyRequest()
                .access("@rbacService.hasPermission(request,authentication)");
                //.access("");
    }

    @Override
    public void configure(WebSecurity web) {
        //将项目中静态资源路径开放出来
        web.ignoring().antMatchers(PERMIT_RES_LIST);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @ConditionalOnMissingBean(AuthenticationManager.class)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 跨站资源共享配置
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(jwtProperties.getCorsAllowedOrigins());
        configuration.setAllowedMethods(jwtProperties.getCorsAllowedMethods());
        configuration.applyPermitDefaultValues();

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public JwtAuthService jwtAuthService(JwtTokenUtil jwtTokenUtil) throws Exception {
        return new JwtAuthService(
                this.authenticationManagerBean(),jwtTokenUtil);
    }

}
