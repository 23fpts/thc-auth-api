package com.thc.serverjwt.controller;

import com.thc.commonutils.R;
import com.thc.serverjwt.dto.SysOrgNode;
import com.thc.serverjwt.entity.SysUser;
import com.thc.serverjwt.service.SysOrgService;
import com.thc.serverjwt.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.controller
 * @Description:
 * @date 2020/10/2 8:11 下午
 */
@RestController
@RequestMapping("sysorg")
public class SysOrgController {

    @Autowired
    private SysOrgService sysOrgService;

    @Autowired
    private SysUserService sysUserService;

    // /sysorg/tree

    @PostMapping("tree")
    public R tree(@RequestParam("username") String username,
                  @RequestParam(value = "orgNameLike", required = false) String orgNameLike,
                  @RequestParam(value = "orgStatus", required = false) Boolean orgStatus) {
        SysUser sysUser = sysUserService.getUserByUsername(username);
        List<SysOrgNode> sysOrgNodes = sysOrgService.getOrgTreeById(sysUser.getOrgId(), orgNameLike, orgStatus);
        return R.ok().data(sysOrgNodes);
    }
}
