package com.thc.jwt.auth.controller;

import com.thc.commonutils.R;
import com.thc.jwt.auth.mapper.MyUserDetailsServiceMapper;
import com.thc.jwt.auth.model.JwtProperties;
import com.thc.jwt.auth.service.JwtAuthService;
import com.thc.jwt.auth.utils.JwtConstants;
import com.thc.servicebase.exceptionhandler.MyException;
import com.thc.servicebase.exceptionhandler.MyExceptionType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author thc
 * @Title:
 * @Package com.thc.jwt.auth.jwt
 * @Description: jwt认证
 * @date 2020/9/24 4:56 下午
 */

@RestController
@ConditionalOnBean({JwtAuthService.class})
// 使用默认的controller
@ConditionalOnProperty(name = "thc.jwt.useDefaultController",
        havingValue = "true")
// prefix为配置文件中的前缀,
// name为配置的名字
// havingValue是与配置的值对比值,当两个值相同返回true,配置类生效.
public class JwtAuthController {

    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private JwtAuthService jwtAuthService;

    @Resource
    private MyUserDetailsServiceMapper myUserDetailsServiceMapper;

    /**
     * 使用用户名密码换JWT令牌
     */
    @RequestMapping(value = JwtConstants.CONTROLLER_AUTHENTICATION)
    public R login(@RequestBody Map<String,String> map){

        String username  = map.get(jwtProperties.getUserParamName());
        String password = map.get(jwtProperties.getPwdParamName());

        if(StringUtils.isEmpty(username)
                || StringUtils.isEmpty(password)){
            return R.error().code(MyExceptionType.USER_INPUT_ERROR.getCode()).message("");
        }
        try {
            return R.ok().data(jwtAuthService.login(username, password,null));
        }catch (MyException e){
            return R.error();
        }
    }

    /**
     * 刷新JWT令牌
     */
    @RequestMapping(value = JwtConstants.CONTROLLER_REFRESH)
    public  R refresh(@RequestHeader("${thc.jwt.header}") String token){
        return R.ok().data(jwtAuthService.refreshToken(token));
    }


    /**
     * 获取用户角色列表接口
     */
    @RequestMapping(value = JwtConstants.CONTROLLER_ROLES)
    public  R roles(
            @RequestHeader("${thc.jwt.header}") String token){
        return R.ok().data(jwtAuthService.roles(token));
    }



}
