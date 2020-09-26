package com.thc.jwt.auth.service;

import com.thc.jwt.auth.mapper.MyUserDetailsServiceMapper;
import com.thc.jwt.auth.utils.JwtTokenUtil;
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
import java.util.List;
import java.util.Map;

/**
 * @author thc
 * @Title:
 * @Package com.thc.jwt.auth.jwt
 * @Description:
 * @date 2020/9/24 5:02 下午
 */
public class JwtAuthService {

    private AuthenticationManager authenticationManager;
    private MyUserDetailsService myUserDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private MyUserDetailsServiceMapper myUserDetailsServiceMapper;

    private JwtAuthService(){}

    public JwtAuthService(AuthenticationManager authenticationManager, MyUserDetailsService myUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     *
     * @param username 用户名
     * @param password 密码
     * @param payloads JWT可以携带的附加信息
     * @return JWT令牌字符串
     * @throws MyException 全局捕获的Exception
     */
    public String login(String username,
                        String password,
                        Map<String,String> payloads)
            throws MyException {
        try {
            UsernamePasswordAuthenticationToken upToken =
                    new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (AuthenticationException e){
            throw new MyException(MyExceptionType.USER_INPUT_ERROR.getCode()
                    ,"用户名或者密码输入错误,或者新建用户未赋予角色权限！");
        }

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails, payloads);
    }


    public String refreshToken(String oldToken){
        if(!jwtTokenUtil.isTokenExpired(oldToken)){
            return jwtTokenUtil.refreshToken(oldToken);
        }
        return null;
    }

    /**
     * 获取角色信息列表
     * @param token
     * @return
     */
    public List<String> roles(String token){
        String username = jwtTokenUtil.getUsernameFromToken(token);
        //加载用户角色列表
        List<String> roleCodes =
                myUserDetailsServiceMapper.findRoleByUserName(username);
        return roleCodes;
    }
}
