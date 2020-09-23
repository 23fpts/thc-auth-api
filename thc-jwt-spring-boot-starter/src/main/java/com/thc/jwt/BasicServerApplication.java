package com.thc.jwt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author thc
 * @Title:
 * @Package com.thc.jwt
 * @Description:
 * @date 2020/9/23 10:54 下午
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.thc.jwt"} )
public class BasicServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicServerApplication.class, args);
    }
}
