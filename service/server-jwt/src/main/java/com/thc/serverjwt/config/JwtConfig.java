package com.thc.serverjwt.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.config
 * @Description: 配置
 * @date 2020/9/20 9:54 下午
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.thc.**.mapper")
public class JwtConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
