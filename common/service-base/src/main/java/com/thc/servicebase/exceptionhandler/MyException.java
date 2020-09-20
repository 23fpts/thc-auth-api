package com.thc.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author thc
 * @Title:
 * @Package com.thc.servicebase.exceptionhandler
 * @Description: 自定义异常
 * @date 2020/8/14 2:33 下午
 */
@Data
// 有参数构造方法
@AllArgsConstructor
// 无参数构造函数
@NoArgsConstructor
public class MyException extends RuntimeException {


    /**
     * 状态吗
     */
    private Integer code;

    /**
     * 异常信息
     */
    private String msg;


}
