package com.thc.serverjwt.controller;

import com.thc.commonutils.R;
import com.thc.serverjwt.entity.SysUser;
import com.thc.serverjwt.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
