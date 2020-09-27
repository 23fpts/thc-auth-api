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
     * 判断某用户是否具有该request资源的访问权限
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication){

        System.out.println("MyRBACService");
        Object principal = authentication.getPrincipal();

        if(principal instanceof UserDetails){

            UserDetails userDetails = ((UserDetails)principal);
            List<GrantedAuthority> authorityList =
                    AuthorityUtils.commaSeparatedStringToAuthorityList(request.getRequestURI());
            return userDetails.getAuthorities().contains(authorityList.get(0))
                    || jwtProperties.getDevOpeningURI().contains(request.getRequestURI());
        }

        return false;
    }


}