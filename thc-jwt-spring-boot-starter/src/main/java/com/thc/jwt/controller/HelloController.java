package com.thc.jwt.controller;

import com.thc.commonutils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thc
 * @Title:
 * @Package com.thc.jwt.controller
 * @Description: test
 * @date 2020/9/24 3:34 下午
 */
@RestController
@RequestMapping("test")
public class HelloController {

    @PostMapping("hello")
    public R hello() {
        return R.ok().data("hello test!");
    }
}
