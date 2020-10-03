package com.thc.serverjwt.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.mapper
 * @Description:
 * @date 2020/10/1 12:32 下午
 */
@SpringBootTest
class SysApiMapperTest {

    @Autowired
    private SysApiMapper apiMapper;

    @Test
    public void test() {
        apiMapper.selectList(null).forEach(System.out::println);
    }

}