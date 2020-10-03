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
 * 第一层实现 : 过滤请求api，通过filter
 * 第二层实现 : RBAC, 加载该用户可以访问的菜单数据，最终展示在如图中的位置，替换掉我们之前在前端写死的菜单数据
 * 第三层实现 : MyRBACService
 * 注意：非常要注意的意见事情是：我们使用了接口级别的鉴权，就不能在开发的时候使用@PathVariable
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
            // 第一步：从jwt令牌中解析出username
            String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

            if(username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null){
                // 第二步：根据用户名加载该用户的用户信息、角色信息、接口权限信息
                //
                UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
                // 第三步：验证JWT令牌的有效性以及是否过期
                if(jwtTokenUtil.validateToken(jwtToken, userDetails)){
                    // 给使用该JWT令牌的用户进行授权
                    // 第四步：如果令牌有效，通过Spring Security对该用户及其接口访问进行授权
                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(
                            userDetails,null, userDetails.getAuthorities());
                    System.out.println("authenticationToken:------");
                    System.out.println(authenticationToken.toString());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        // 第五步： 没有被授权的访问请求，将被Spring Security拦截，返回403 访问禁止。
        filterChain.doFilter(request,response);
        System.out.println("doFilterInternal end");
    }
}