package com.thc.jwt.config;

import com.thc.jwt.auth.MyAuthenticationFailureHandler;
import com.thc.jwt.auth.MyAuthenticationSuccessHandler;
import com.thc.jwt.auth.MyExpireSessionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;


/**
 * @author thc
 * @Title:
 * @Package com.thc.jwt.config
 * @Description:
 * @date 2020/9/21 4:42 下午
 */
@Configuration
public class JwtWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Resource
    MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Resource
    MyAuthenticationFailureHandler myAuthenticationFailureHandler;


    /**
     *
     *
     */

    /**
     * 简陋认证 httpBasic 可以被轻易破解
     */




    /**
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic()
//                .and()
//                // 针对所有请求需要先登录
//                .authorizeRequests().anyRequest()
//                // 登录认证
//                .authenticated();

        http.csrf().disable()// 跨站防御攻击关闭
                .formLogin()
                .loginPage("login.html")
                .loginProcessingUrl("/login")
                //
                // .defaultSuccessUrl("index")
                // .failureUrl("/login.html")
                // 成功或者失败后执行自定义业务，和defaultSuccessUrl failureUrl只能二者选一个
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("login.html", "/login").permitAll()
                .antMatchers("/biz1", "biz2") // 需要对外暴露的资源路径（页面）
                .hasAnyAuthority("ROLE_user", "ROLE_admin")
                .antMatchers("/syslog", "/sysuser")
                // .hasAnyRole("admin") // admin角色才可以访问的页面
                // 或者
                .hasAnyAuthority("ROLE_admin")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()  // session 管理
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)// session 创建策略 IF_REQUIRED为默认的
                // session 超时时间配置，重要
                // 超时后重新跳转
                .invalidSessionUrl("login.html")
                // 每次登录session重新生成一session id ，4种
                .sessionFixation()
                // 重新把session复制一份生成一个session
                .migrateSession()
                // maximumSession 最大登录用户数量
                .maximumSessions(1)
                // 提供session保护策略，true表示登录后不允许再次登录，false表示允许再次登录但是之前的登录状态会下线
                .maxSessionsPreventsLogin(false)
                // session 超时和限制登录人数
                .expiredSessionStrategy(new MyExpireSessionStrategy());




    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("123456"))
                .roles("user")
                .and()
                .withUser("admin")
                .password(passwordEncoder().encode("123456"))
                .roles("admin")
                .and()
                .passwordEncoder(passwordEncoder()); // 配置BCrypt加密
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 将项目中静态资源路径开放出来
        web.ignoring().antMatchers("/css/**", "/font/**", "/img/**", "/js/**");
    }

    /**
     * 密码加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
