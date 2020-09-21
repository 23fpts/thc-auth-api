package com.thc.commonutils;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author thc
 * @Title:
 * @Package com.thc.commonutils
 * @Description: 返回结果类
 * @date 2020/8/12 2:58 下午
 */
@Data
public class R {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回信息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Object data;

    /**
     * 把构造方法私有化
     */
    private R() {}

    // 链式编程
    // 例如 R.ok().success(true).code(20001).message("test")

    /**
     *
     * 成功静态方法
     * @return
     */
    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage(ResultMessage.SUCCESS);
        return r;
    }

    /**
     * 失败静态方法
     * @return
     */
    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage(ResultMessage.ERROR);
        return r;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(Object value){
        this.setData(value);
        return this;
    }

    /**
     * 此方法直接吧data令为map类型，不能data().mapData()
     * @param key
     * @param value
     * @return
     */
    public R mapData(String key, Object value) {
        Map<String, Object> result = new HashMap<>();
        // 如果data存在值且是map类型，就把原来map的所有内容赋值到result，否则直接覆盖原数据
        if (this.getData()!=null && this.getData() instanceof Map) {
             result.putAll((Map<String, Object>)this.getData());
        }
        result.put(key, value);
        this.setData(result);
        return this;
    }


}
