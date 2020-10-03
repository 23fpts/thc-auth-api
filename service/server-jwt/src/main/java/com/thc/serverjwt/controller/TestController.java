package com.thc.serverjwt.controller;

import com.thc.commonutils.R;
import com.thc.servicebase.exceptionhandler.MyException;
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


        System.out.println(123);
        System.out.println();
        return R.ok().data("test ok!");
    }

    @GetMapping("test2")
    public R test2() {
        throw new MyException(123, "test2");
    }

    @GetMapping("test3")
    public String test3() {
        return "test3";
    }


}
