package com.thc.serverjwt.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.service
 * @Description:
 * @date 2020/10/1 12:50 下午
 */
@SpringBootTest
class SysUserServiceTest {

    @Autowired
    private SysUserService userService;

    @Test
    public void Test() {
        String username = "admin1";
        System.out.println(        userService.getUserByUsername(username)
        .toString());
    }

}