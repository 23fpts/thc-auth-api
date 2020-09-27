package com.thc.serverjwt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt
 * @Description: 启动类
 * @date 2020/9/20 9:52 下午
 */
// @ComponentScan(basePackages = {"com.thc"}) 用于设置包的扫描规则，可以扫描到其他模块中的类，不写就只会默认扫描当前模块


//@SpringBootApplication
@ComponentScan(basePackages = {"com.thc"})
@SpringBootApplication(scanBasePackages={"com.thc"})
public class JwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtApplication.class, args);
    }

}
