package com.thc.jwt.service;

import com.thc.jwt.model.JwtProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author thc
 * @Title:
 * @Package com.thc.service
 * @Description:
 * @date 2020/9/27 7:47 下午
 */

@Component("rbacService")
public class MyRBACService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Resource
    private JwtProperties jwtProperties;

    /**
     * userDetails.getAuthorities() : 在授权时候（MyRBACService中的授权）
     *
     * 用request.getRequestURI获得当前请求的URI，如：“/sysuser/info”。
     *
     * 当authorityLists包含request.getRequestURI的时候，表示当前接口可以被访问。
     *
     * 判断某用户是否具有该request资源的访问权限
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication){

        System.out.println("MyRBACService");
        Object principal = authentication.getPrincipal();
        System.out.println(principal instanceof UserDetails);
        if(principal instanceof UserDetails){

            UserDetails userDetails = ((UserDetails)principal);
            List<GrantedAuthority> authorityList =
                    AuthorityUtils.commaSeparatedStringToAuthorityList(request.getRequestURI());
            System.out.println("getDevOpeningURI");
            System.out.println(jwtProperties.getDevOpeningURI().contains(request.getRequestURI()));
            System.out.println(userDetails.getAuthorities().contains(authorityList.get(0))
                    || jwtProperties.getDevOpeningURI().contains(request.getRequestURI()));
            return userDetails.getAuthorities().contains(authorityList.get(0))
                    || jwtProperties.getDevOpeningURI().contains(request.getRequestURI()); // 开发过程中临时开放的api接口
        }

        return false;
    }


}