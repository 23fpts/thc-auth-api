package com.thc.jwt.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thc.commonutils.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author thc
 * @Title:
 * @Package com.thc.jwt.auth
 * @Description: 认证失败的处理
 * @date 2020/9/22 4:10 下午
 */
@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${spring.security.loginType}")
    private String loginType;

    // jackson  提供的json字符串和对象之间的转换
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        if (loginType.equalsIgnoreCase("JSON")) {
            // 如果是JSON数据
            // equalsIgnoreCase() 方法用于将字符串与指定的对象比较，不考虑大小写。
            // 返回JSON
            response.setContentType("application/json;charset=UTF-8");
            // 返回结果转为json
            response.getWriter().write(objectMapper.writeValueAsString(R.error().code(12345).message("用户名或者密码错误")));
        } else {
            // 不是JSON数据
            // 调用父类的方法，跳转到登录之前请求的页面
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
