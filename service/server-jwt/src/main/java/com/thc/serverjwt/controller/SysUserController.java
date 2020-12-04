package com.thc.serverjwt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thc.commonutils.R;
import com.thc.serverjwt.entity.SysUser;
import com.thc.serverjwt.entity.SysUserOrg;
import com.thc.serverjwt.mapper.SystemMapper;
import com.thc.serverjwt.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.controller
 * @Description:
 * @date 2020/10/1 4:19 下午
 */
@RestController
@RequestMapping("/sysuser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SystemMapper systemMapper;

    /**
     * /sysuser/info
     * @param username
     * @return
     */
    @GetMapping("info")
    public R info(@RequestParam(value = "username", defaultValue = "", required = true) String username) {
        SysUser sysUser = sysUserService.getUserByUsername(username);
        return R.ok().data(sysUser);
    }

    @PostMapping("query")
    public R query(@RequestParam(value = "orgId", defaultValue = "", required = false) Integer orgId ,
                   @RequestParam(value = "username", defaultValue = "", required = false) String username ,
                   @RequestParam(value = "phone", defaultValue = "", required = false) String phone,
                   @RequestParam(value = "email", defaultValue = "", required = false) String email,
                   @RequestParam(value = "enabled", defaultValue = "", required = false) Boolean enabled,
                   @RequestParam(value = "createStartTime", defaultValue = "", required = false) Date createStartTime,
                   @RequestParam(value = "createEndTime", defaultValue = "", required = false) Date createEndTime,
                   @RequestParam(value = "pageNum", defaultValue = "", required = false) Integer pageNum,
                   @RequestParam(value = "pageSize", defaultValue = "", required = false) Integer pageSize){
        if (pageNum==null){
            pageNum = 1;
        }
        if (pageSize==null) {
            pageSize = 10;
        }
        Page<SysUserOrg> sysUserOrgPage = sysUserService.queryUser(new Page<>(pageNum, pageSize), orgId, username, phone, email, enabled, createStartTime, createEndTime);
        return R.ok().data(sysUserOrgPage);
    }

    @PostMapping("update")
    public R update(@RequestBody SysUser sysUser) {
        sysUserService.updateUser(sysUser);
        return R.ok();
    }

    @PostMapping(value = "add")
    public R add(@RequestBody SysUser sysUser) {
        sysUserService.addUser(sysUser);
        return R.ok();
    }

    @PostMapping("delete")
    public R delete(@RequestParam Integer userId) {
        sysUserService.delete(userId);
        return R.ok();
    }

    @PostMapping(value = "/pwd/reset")
    public R pwdreset(@RequestParam Integer userId) {
        sysUserService.pwdreset(userId);
        return R.ok().message("重置密码成功");
    }

    //当前用户是否仍在使用默认密码
    @PostMapping(value = "/pwd/isdefault")
    public R isdefault(@RequestParam String username) {
        return R.ok().data(sysUserService.isdefault(username));
    }
    //根据用户名更新密码
    @PostMapping(value = "/pwd/change")
    public R pwdchange(@RequestParam String username,
                                  @RequestParam String oldPass,
                                  @RequestParam String newPass) {
        sysUserService.changePwd(username,oldPass,newPass);
        return R.ok().message("修改密码成功");
    }



}
