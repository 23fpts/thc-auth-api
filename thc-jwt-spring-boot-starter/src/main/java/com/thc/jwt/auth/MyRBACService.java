package com.thc.jwt.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author thc
 * @Title:
 * @Package com.thc.jwt.auth
 * @Description:
 * @date 2020/9/23 11:38 下午
 */
@Component("rbacService")
public class MyRBACService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Resource
    private MyRBACServiceMapper myRBACServiceMapper;

    /**
     * 判断某用户是否具有该request资源的访问权限
     * @param request
     * @param authentication
     * @return
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        // 被验证的用户主体
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();

            List<String> urls = myRBACServiceMapper.findUrlsByUsername(username);

            // 判断request中请求的是否在urls中
            // request.getRequestURI()

            return urls.stream().anyMatch(
                    url -> antPathMatcher.match(url, request.getRequestURI())
            );
        }
        return false;
    }
}
