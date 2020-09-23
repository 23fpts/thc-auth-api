package com.thc.jwt;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author thc
 * @Title:
 * @Package com.thc.jwt
 * @Description:
 * @date 2020/9/23 10:58 下午
 */

@SpringBootTest
public class BasicServerApplicationTest {

    @Resource
    PasswordEncoder passwordEncoder;

    @Test
    public void contextLoads() {
        System.out.println(passwordEncoder.encode("123456"));
    }
}