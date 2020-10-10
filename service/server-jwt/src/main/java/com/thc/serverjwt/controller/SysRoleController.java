package com.thc.serverjwt.controller;

import com.thc.commonutils.R;
import com.thc.serverjwt.entity.SysRole;
import com.thc.serverjwt.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.controller
 * @Description:
 * @date 2020/10/8 8:06 下午
 */
@RestController
@RequestMapping("sysrole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("query")
    public R query(@RequestParam(value = "roleLike", required = false, defaultValue = "") String roleLike) {
        return R.ok().data(sysRoleService.queryRoles(roleLike));
    }

    @PostMapping("update")
    public R update(@RequestBody SysRole sysRole) {
        sysRoleService.updateRole(sysRole);
        return R.ok();
    }

    @PostMapping("add")
    public R add(@RequestBody SysRole sysRole) {
        sysRoleService.addRole(sysRole);
        return R.ok();
    }

    @PostMapping("delete")
    public R delete(@RequestBody SysRole sysRole) {
        sysRoleService.deleteRole(sysRole);
        return R.ok();
    }
}
