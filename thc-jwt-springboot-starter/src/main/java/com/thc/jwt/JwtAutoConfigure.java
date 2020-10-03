package com.thc.jwt;

import com.thc.jwt.config.JwtAuthenticationTokenFilter;
import com.thc.jwt.model.JwtProperties;
import com.thc.jwt.service.MyUserDetailsService;
import com.thc.jwt.utils.JwtTokenUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author thc
 * @Title:
 * @Package com.thc
 * @Description:
 * @date 2020/9/27 7:41 下午
 */

/**
 * 记录错误的修改记录 TODO
 * componentScan的地址要对，
 * mapperScan的也是，要注意，最好配置为com.thc.**.mapper
 *
 */

@Configuration
// 如果thc.jwt.enabled 的值和havingValue的值不一样就不会启动配置。
@ConditionalOnProperty(name = "thc.jwt.enabled", havingValue = "true")
@EnableConfigurationProperties(JwtProperties.class)
public class JwtAutoConfigure {

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
