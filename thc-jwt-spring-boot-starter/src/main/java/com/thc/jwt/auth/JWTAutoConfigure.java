package com.thc.jwt.auth;


import com.thc.jwt.auth.config.JwtAuthenticationTokenFilter;
import com.thc.jwt.auth.model.JwtProperties;
import com.thc.jwt.auth.service.MyUserDetailsService;
import com.thc.jwt.auth.utils.JwtTokenUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
// 如果thc.jwt.enabled 的值和havingValue的值不一样就不会启动配置。
@ConditionalOnProperty(name = "thc.jwt.enabled", havingValue = "true")
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
