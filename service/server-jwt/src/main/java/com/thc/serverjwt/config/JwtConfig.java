package com.thc.serverjwt.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.config
 * @Description: 配置
 * @date 2020/9/20 9:54 下午
 */
@Configuration
@MapperScan("com.thc.**.mapper")
public class JwtConfig {
}
