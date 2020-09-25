package com.thc.jwt.auth.jwt;

import com.thc.commonutils.R;
import com.thc.servicebase.exceptionhandler.MyException;
import com.thc.servicebase.exceptionhandler.MyExceptionType;
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
@RequestMapping("")
public class JwtAuthController {

    @Resource
    private JwtAuthService jwtAuthService;

    // authentication
    @RequestMapping(value = "/authentication")
    public R login(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return R.error().message("用户名or密码不能为空");
        }
        System.out.println(1);
        try {
            String jwt = jwtAuthService.login(username, password);
            System.out.println(2);
            return R.ok().data(jwt);
        } catch (MyException e) {
            System.out.println(3);
            throw new MyException(MyExceptionType.USER_INPUT_ERROR.getCode(), MyExceptionType.USER_INPUT_ERROR.getTypeDesc());
        }
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    @RequestMapping(value = "/refreshtoken")
    public R refresh(@RequestHeader("${jwt.header}") String token) {
        return R.ok().data(jwtAuthService.refreshToken(token));
    }

    @GetMapping("test")
    public R testError(){
        throw new MyException(123, "test error!");
    }

}
