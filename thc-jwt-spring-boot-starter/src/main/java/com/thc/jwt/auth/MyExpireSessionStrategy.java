package com.thc.jwt.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author thc
 * @Title:
 * @Package com.thc.jwt.auth
 * @Description: session超时策略
 * @date 2020/9/22 5:02 下午
 */
public class MyExpireSessionStrategy implements SessionInformationExpiredStrategy {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "您已经在另外一台电脑或浏览器被迫下线");
        // JSON返回
        sessionInformationExpiredEvent.getResponse().setContentType("application/json;charset=UTF-8");
        // 写回数据
        sessionInformationExpiredEvent.getResponse().getWriter().write(objectMapper.writeValueAsString(map));

    }
}
