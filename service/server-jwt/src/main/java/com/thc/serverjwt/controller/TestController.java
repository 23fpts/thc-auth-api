package com.thc.serverjwt.controller;

import com.thc.commonutils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.controller
 * @Description: 测试
 * @date 2020/9/20 9:55 下午
 */
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("test")
    public R test() {
        return R.ok().data("test ok!");
    }
}
