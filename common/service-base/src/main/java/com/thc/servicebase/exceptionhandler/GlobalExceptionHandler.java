package com.thc.servicebase.exceptionhandler;


import com.thc.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author thc
 * @Title:
 * @Package com.thc.servicebase.exceptionhandler
 * @Description: 全局异常处理类，所有异常都调用这个方法
 * @date 2020/8/12 11:02 下午
 */

@ControllerAdvice
// logback 日志的异常的注解
@Slf4j
public class GlobalExceptionHandler {

    // 指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody // 为了返回数据
    public R error (Exception e) {
        e.printStackTrace();
        return R.error().message("全局异常");
    }

    // ArithmeticException 特定异常 (系统自带的异常)
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody // 为了返回数据
    public R error (ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("ArithmeticException异常");
    }

    // MyException 信息获取
    @ExceptionHandler(MyException.class)
    @ResponseBody // 为了返回数据
    public R error (MyException e) {
        // 日志
        System.out.println("MyException");
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }



}
