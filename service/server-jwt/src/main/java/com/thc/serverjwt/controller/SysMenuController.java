package com.thc.serverjwt.controller;

import com.thc.commonutils.R;
import com.thc.serverjwt.dto.RoleCheckedIds;
import com.thc.serverjwt.dto.SysMenuNode;
import com.thc.serverjwt.entity.SysMenu;
import com.thc.serverjwt.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * checkedtree 查询菜单树、默认展开节点、默认勾选节点
     * @param roleId
     * @return
     */
    @PostMapping("checkedtree")
    public R checkedTree(@RequestParam(value = "roleId") Integer roleId) {
        Map<String, Object> result = new HashMap<>();
        // 全部menu的额tree结构
        result.put("tree", sysMenuService.getMenuTreeById("", null));
        // 获得所有展开的id（level为2）
        result.put("expandedKeys", sysMenuService.getExpandedKeys());
        // 获取所有有权限的menu的id
        result.put("checkedKeys", sysMenuService.getCheckedKeys(roleId));
        return R.ok().data(result);
    }

    @PostMapping("savekeys")
    public R saveKeys(@RequestBody RoleCheckedIds roleCheckedIds) {
        sysMenuService.saveCheckedKeys(roleCheckedIds.getRoleId(), roleCheckedIds.getCheckedIds());
        return R.ok();
    }
}
