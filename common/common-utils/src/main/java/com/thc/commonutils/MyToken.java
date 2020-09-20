package com.thc.commonutils;

import java.util.UUID;

/**
 * @author thc
 * @Title:
 * @Package com.thc.commonutils
 * @Description:
 * @date 2020/9/1 7:21 下午
 */
public class MyToken {

    public static String getMyToken() {
        String token = UUID.randomUUID().toString().replace("-", "");
        return token;
    }
}
