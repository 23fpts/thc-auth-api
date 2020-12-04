package com.thc.serverjwt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thc.serverjwt.entity.SysUser;
import com.thc.serverjwt.entity.SysUserOrg;
import com.thc.serverjwt.mapper.SysUserMapper;
import com.thc.serverjwt.mapper.SystemMapper;
import com.thc.servicebase.exceptionhandler.MyException;
import com.thc.servicebase.exceptionhandler.MyExceptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

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

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public Page<SysUserOrg> queryUser(Page<SysUserOrg> page, Integer orgId, String username, String phone, String email, Boolean enabled, Date createStartTime, Date createEndTime) {
        return systemMapper.selectUser(page, orgId, username, phone, email, enabled, createStartTime, createEndTime);
    }

    public void updateUser (SysUser sysUser) {
        if (sysUser.getId() == null) {
            throw new MyException(MyExceptionType.USER_INPUT_ERROR.getCode(), MyExceptionType.USER_INPUT_ERROR.getTypeDesc());
        }else {
            userMapper.updateById(sysUser);
        }
    }

    public void addUser(SysUser sysUser) {
        // TODO 初始密码可以优化为通用配置
        sysUser.setPassword(passwordEncoder.encode("123456"));
        sysUser.setCreateTime(new Date());
        sysUser.setEnabled(true); // 新增用户激活
        userMapper.insert(sysUser);
    }

    public void delete(Integer id) {
        userMapper.deleteById(id);
    }


    public void pwdreset(Integer userId){
        if(userId == null){
            throw new MyException(MyExceptionType.USER_INPUT_ERROR.getCode(),
                    "重置密码必须带主键");
        }else{
            SysUser sysUser = new SysUser();
            sysUser.setId(userId);
            //TODO 初始密码可以优化为通用配置
            sysUser.setPassword(passwordEncoder.encode("123456"));
            userMapper.updateById(sysUser);
        }
    }

    //校验当前用户是否仍在使用默认密码
    public Boolean isdefault(String username){
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        SysUser sysUser = userMapper.selectOne(wrapper);
        //判断数据库密码是否是默认密码
        return passwordEncoder.matches("123456",sysUser.getPassword());
    }

    //修改密码（当前用户、旧密码、新密码）
    public void changePwd(String username, String oldPass, String newPass){
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        SysUser sysUser = userMapper.selectOne(wrapper);
        //判断旧密码是否正确
        boolean isMatch = passwordEncoder.matches(oldPass,sysUser.getPassword());

        if(isMatch){ //如果旧密码正确，更新新密码
            SysUser sysUser1 = new SysUser();
            sysUser.setId(sysUser.getId());
            sysUser.setPassword(passwordEncoder.encode(newPass));
            userMapper.updateById(sysUser);
        }else{
            throw new MyException(MyExceptionType.USER_INPUT_ERROR.getCode(),
                    "原密码输入错误，请确认后重新输入！");
        }

    }

}
