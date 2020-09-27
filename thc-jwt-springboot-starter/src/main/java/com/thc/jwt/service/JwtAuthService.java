package com.thc.jwt.service;

import com.thc.jwt.mapper.MyUserDetailsServiceMapper;
import com.thc.servicebase.exceptionhandler.MyException;
import com.thc.servicebase.exceptionhandler.MyExceptionType;
import com.thc.jwt.utils.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author thc
 * @Title:
 * @Package com.thc.service
 * @Description:
 * @date 2020/9/27 7:48 下午
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
     * 登录认证换取JWT令牌
     * @return JWT
     */
    public String login(String username,
                        String password,
                        Map<String,String> payloads)
            throws MyException {
        System.out.println("JwtAuthService");
        try {
            UsernamePasswordAuthenticationToken upToken =
                    new UsernamePasswordAuthenticationToken(username, password);
            System.out.println(upToken.toString());
            System.out.println("authenticationManager start");
            Authentication authentication = authenticationManager.authenticate(upToken);
            System.out.println("authenticationManager end");
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (AuthenticationException e){
            throw new MyException(MyExceptionType.USER_INPUT_ERROR.getCode()
                    ,"用户名或者密码输入错误, 或者新建用户未赋予角色权限！");
        }
        System.out.println("JwtAuthService 2");
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails,payloads);
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
