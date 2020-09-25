package com.thc.servicebase.exceptionhandler;

/**
 * @author thc
 * @Title:
 * @Package com.thc.servicebase.exceptionhandler
 * @Description:
 * @date 2020/9/24 5:08 下午
 */
public enum MyExceptionType {
    USER_INPUT_ERROR(400,"用户输入异常"),
    SYSTEM_ERROR (500,"系统服务异常"),
    OTHER_ERROR(999,"其他未知异常");

    MyExceptionType(int code, String typeDesc) {
        this.code = code;
        this.typeDesc = typeDesc;
    }

    private String typeDesc;//异常类型中文描述

    private int code; //code

    public String getTypeDesc() {
        return typeDesc;
    }

    public int getCode() {
        return code;
    }
}
