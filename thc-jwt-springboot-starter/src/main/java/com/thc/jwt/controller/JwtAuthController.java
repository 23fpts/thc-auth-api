package com.thc.jwt.controller;

import com.thc.commonutils.R;
import com.thc.jwt.mapper.MyUserDetailsServiceMapper;
import com.thc.jwt.model.JwtProperties;
import com.thc.jwt.service.JwtAuthService;
import com.thc.servicebase.exceptionhandler.MyException;
import com.thc.servicebase.exceptionhandler.MyExceptionType;
import com.thc.jwt.utils.JwtConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author thc
 * @Title:
 * @Package com.thc.controller
 * @Description:
 * @date 2020/9/27 7:52 下午
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


    /**
     * 使用用户名密码换JWT令牌
     */
    @RequestMapping(value = JwtConstants.CONTROLLER_AUTHENTICATION)
    public R login(@RequestBody Map<String,String> map){

        System.out.println("JwtAuthController");
        String username  = map.get(jwtProperties.getUserParamName());
        String password = map.get(jwtProperties.getPwdParamName());

        if(StringUtils.isEmpty(username)
                || StringUtils.isEmpty(password)){
            return R.error().code(MyExceptionType.USER_INPUT_ERROR.getCode()).message("用户名密码不能为空");
        }
//        try {
//            System.out.println("try");
//            return R.ok().data(jwtAuthService.login(username, password,null));
//        }catch (MyException e){
//            System.out.println("error");
//            return R.error().message(e.getMessage());
//        }
       return R.ok().data(jwtAuthService.login(username, password,null));

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