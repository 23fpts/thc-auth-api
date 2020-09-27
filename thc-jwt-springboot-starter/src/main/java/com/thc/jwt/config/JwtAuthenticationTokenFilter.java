package com.thc.jwt.config;

/**
 * @author thc
 * @Title:
 * @Package com.thc.config
 * @Description:
 * @date 2020/9/27 7:53 下午
 */

import com.thc.jwt.model.JwtProperties;
import com.thc.jwt.service.MyUserDetailsService;
import com.thc.jwt.utils.JwtTokenUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT令牌授权过滤器
 * 1.判断令牌的有效性
 * 2.根据令牌为该用户授权可以访问的资源
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private JwtProperties jwtProperties;
    private JwtTokenUtil jwtTokenUtil;
    private MyUserDetailsService myUserDetailsService;

    private JwtAuthenticationTokenFilter(){}

    public JwtAuthenticationTokenFilter(JwtProperties jwtProperties,
                                        JwtTokenUtil jwtTokenUtil,
                                        MyUserDetailsService myUserDetailsService) {
        this.jwtProperties = jwtProperties;
        this.jwtTokenUtil = jwtTokenUtil;
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("doFilterInternal");
        String jwtToken = request.getHeader(jwtProperties.getHeader());
        if(!StringUtils.isEmpty(jwtToken)){
            String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

            if(username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
                if(jwtTokenUtil.validateToken(jwtToken,userDetails)){
                    //给使用该JWT令牌的用户进行授权
                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(
                            userDetails,null, userDetails.getAuthorities());
                    System.out.println("authenticationToken:------");
                    System.out.println(authenticationToken.toString());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(request,response);
        System.out.println("doFilterInternal end");
    }
}