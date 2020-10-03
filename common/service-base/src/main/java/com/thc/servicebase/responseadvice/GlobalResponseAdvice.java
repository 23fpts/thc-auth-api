package com.thc.servicebase.responseadvice;

import com.thc.commonutils.R;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author thc
 * @Title:
 * @Package com.thc.servicebase.responseadvice
 * @Description: controller层返回数据优化和统一处理
 * @date 2020/10/1 3:03 下午
 */
@Component
@ControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice {
    // supports方法返回true的时候，进入beforeBodyWrite方法进行数据结构转换
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        //return returnType.hasMethodAnnotation(ResponseBody.class);
        return true;
    }
    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        if(selectedContentType.equalsTypeAndSubtype(
                MediaType.APPLICATION_JSON)){
            // 如果controller返回的是封装了的R类型数据，就不处理，否则自动封装到R中
            if (body instanceof R) {
                R r = (R) body;
                // 业务状态与HTTP协议Response状态码一致
                response.setStatusCode(HttpStatus.valueOf(r.getCode()));
                return body;
            } else {
                return body;
                // TODO 不知道为啥有些错误没有被MyException包含，先不做下面
                // return R.ok().data(body);
            }
        }
        return body;
    }
}