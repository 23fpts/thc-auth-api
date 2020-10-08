package com.thc.serverjwt.controller;

import com.thc.commonutils.R;
import com.thc.serverjwt.dto.SysMenuNode;
import com.thc.serverjwt.entity.SysMenu;
import com.thc.serverjwt.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.controller
 * @Description:
 * @date 2020/10/5 3:17 下午
 */
@RestController
@RequestMapping("sysmenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * /sysmenu/tree
     * @param meuNameLike 模糊查询name
     * @param menuStatus 查询状态条件
     * @return
     */
    @PostMapping("tree")
    public R tree (@RequestParam(value = "menuNameLike", required = false) String meuNameLike,
                   @RequestParam(value = "menuStatus", required = false) Boolean menuStatus) {
        List<SysMenuNode> sysMenuNodes = sysMenuService.getMenuTreeById(meuNameLike, menuStatus);
        System.out.println(sysMenuNodes);
        return R.ok().data(sysMenuNodes);
    }

    @PostMapping("add")
    public R add (@RequestBody SysMenu sysMenu) {
        sysMenuService.addMenu(sysMenu);
        return R.ok();
    }

    @PostMapping("update")
    public R update(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateMenu(sysMenu);
        return R.ok();
    }

    @PostMapping("delete")
    public R delete(@RequestBody SysMenu sysMenu) {
        sysMenuService.delete(sysMenu);
        return R.ok();
    }
}
