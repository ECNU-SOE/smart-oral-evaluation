package net.ecnu;

import net.ecnu.config.JwtAuthenticationTokenFilter;
import net.ecnu.model.JwtProperties;
import net.ecnu.service.MyUserDetailsService;
import net.ecnu.utils.JwtTokenUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@ConditionalOnProperty(name = "soe.jwt.enabled",havingValue = "true")
@EnableConfigurationProperties(JwtProperties.class)
public class JWTAutoConfigure {
    @Resource
    private JwtProperties jwtProperties;


    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil(jwtProperties);
    }


    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(
            JwtTokenUtil jwtTokenUtil,
            MyUserDetailsService myUserDetailsService) {
        return new JwtAuthenticationTokenFilter(
                this.jwtProperties,jwtTokenUtil,myUserDetailsService);
    }
}
