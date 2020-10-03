package com.thc.serverjwt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thc.serverjwt.entity.SysUser;
import com.thc.serverjwt.mapper.SysUserMapper;
import com.thc.servicebase.exceptionhandler.MyException;
import com.thc.servicebase.exceptionhandler.MyExceptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.service
 * @Description:
 * @date 2020/10/1 12:41 下午
 */
@Service
public class SysUserService {

    @Autowired
    private SysUserMapper userMapper;

    public SysUser getUserByUsername(String username) {
        if (!StringUtils.isEmpty(username)) {
            QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
            wrapper.eq("username", username);
            SysUser sysUser = userMapper.selectOne(wrapper);
            if (sysUser!=null) {
                // 密码不传
                sysUser.setPassword("");
                return sysUser;
            } else {
                throw new MyException(MyExceptionType.USER_INPUT_ERROR.getCode(), "查询参数用户名不存在");
            }

        } else {
            throw new MyException(MyExceptionType.USER_INPUT_ERROR.getCode(), "查询参数用户名不存在");
        }

    }
}
