package com.thc.jwt.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thc.commonutils.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author thc
 * @Title:
 * @Package com.thc.jwt.auth
 * @Description:
 * @date 2020/9/21 10:24 下午
 */
// 一般继承 implements AuthenticationSuccessHandler
// 但是可以继承 SavedRequestAwareAuthenticationSuccessHandler， 它提供了一些实现
// 写完之后还需要在config中配置才能用
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Value("${spring.security.loginType}")
    private String loginType;

    // jackson  提供的json字符串和对象之间的转换
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 可以在登录成功后自动跳转到之前被拒绝访问的页面
     *
     * @param request
     * @param response
     * @param authentication
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws ServletException, IOException {
        if (loginType.equalsIgnoreCase("JSON")) {
            // equalsIgnoreCase() 方法用于将字符串与指定的对象比较，不考虑大小写。
            // 返回JSON
            response.setContentType("application/json;charset=UTF-8");
            // 返回结果转为json
            response.getWriter().write(objectMapper.writeValueAsString(R.ok()));
        } else {
            // 调用夫类的方法，跳转到登录之前请求的页面
            super.onAuthenticationSuccess(request, response, authentication);
        }


    }
}
