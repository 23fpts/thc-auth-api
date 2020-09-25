package com.thc.jwt.auth.jwt;

import com.thc.jwt.auth.MyUserDetails;
import com.thc.jwt.auth.MyUserDetailsService;
import com.thc.servicebase.exceptionhandler.MyException;
import com.thc.servicebase.exceptionhandler.MyExceptionType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author thc
 * @Title:
 * @Package com.thc.jwt.auth.jwt
 * @Description:
 * @date 2020/9/24 5:02 下午
 */
@Service
public class JwtAuthService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 登录认证换取jwt令牌
     * @return jwt token
     */
    public String login(String username, String password) throws MyException{
        System.out.println(1.1);
        try {
            System.out.println(1.3);
            UsernamePasswordAuthenticationToken uptoken = new UsernamePasswordAuthenticationToken(username, password);
            System.out.println(uptoken.getName());
            Authentication authentication = authenticationManager.authenticate(uptoken);
            // TODO 没出来 authentication
            System.out.println(1.6);
            System.out.println(authentication.getName());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println(1.7);
        } catch (AuthenticationException e) {
            System.out.println(1.4);
            throw new MyException(MyExceptionType.USER_INPUT_ERROR.getCode(), "用户名或者密码不正确");
        }
        // 用户信息和角色信息
        UserDetails myUserDetails = userDetailsService.loadUserByUsername(username);
        System.out.println(1.2);
        System.out.println(myUserDetails.getUsername()+", "+myUserDetails.getPassword());
        return jwtTokenUtil.generateToken(myUserDetails);
    }

    public String refreshToken(String oldToken) {
        if (!jwtTokenUtil.isTokenExpired(oldToken)) {
            // 没有过期
            return jwtTokenUtil.refreshToken(oldToken);
        }
        // 过期了就返回null
        return null;
    }
}
