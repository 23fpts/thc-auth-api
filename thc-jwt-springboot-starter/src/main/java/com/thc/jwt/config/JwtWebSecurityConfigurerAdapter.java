package com.thc.jwt.config;

/**
 * @author thc
 * @Title:
 * @Package com.thc.config
 * @Description:
 * @date 2020/9/27 7:54 下午
 */

import com.thc.jwt.model.JwtProperties;
import com.thc.jwt.service.JwtAuthService;
import com.thc.jwt.service.MyUserDetailsService;
import com.thc.jwt.utils.JwtConstants;
import com.thc.jwt.utils.JwtTokenUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;

/**
 * Spring Security 配置
 * 可以配置多个WebSecurityConfigurerAdapter
 * 但是多个Adaptor有执行顺序，默认值是100
 * 这里设置为1会优先执行
 */
@Configuration
@Order(1)
public class JwtWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private MyUserDetailsService myUserDetailsService;

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        if(jwtProperties.getCsrfDisabled()){
            http = http.csrf().disable();
        }
        http.cors()
                .and().addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(
                        JwtConstants.CONTROLLER_AUTHENTICATION,
                        JwtConstants.CONTROLLER_REFRESH,
                        JwtConstants.CONTROLLER_ROLES
                ).permitAll();

        //通过配置实现的不需要JWT令牌就可以访问的接口
        for(String uri : jwtProperties.getPermitAllURI()){
            http.authorizeRequests().antMatchers(uri).permitAll();
        }
        //RBAC权限控制级别的接口权限校验
        http.authorizeRequests().anyRequest()
                .access("@rbacService.hasPermission(request,authentication)");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
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

    @Override
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @ConditionalOnMissingBean(AuthenticationManager.class)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthService jwtAuthService(
            MyUserDetailsService myUserDetailsService,
            JwtTokenUtil jwtTokenUtil
    ) throws Exception {
        return new JwtAuthService(
                this.authenticationManagerBean(),myUserDetailsService,jwtTokenUtil);
    }


}
