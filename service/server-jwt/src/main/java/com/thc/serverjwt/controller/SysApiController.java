package com.thc.serverjwt.controller;

import com.thc.commonutils.R;
import com.thc.serverjwt.dto.RoleCheckedIds;
import com.thc.serverjwt.dto.SysApiNode;
import com.thc.serverjwt.entity.SysApi;
import com.thc.serverjwt.entity.SysMenu;
import com.thc.serverjwt.service.SysApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.controller
 * @Description: :8101/sysapi/tree
 * @date 2020/10/29 7:26 下午
 */
@RestController
@RequestMapping("sysapi")
public class SysApiController {

    @Autowired
    private SysApiService sysApiService;

    @PostMapping("tree")
    public R tree(@RequestParam(value = "apiNameLike", required = false) String apiNameLike,
                  @RequestParam(value = "apiStatus", required = false) Boolean apiStatus) {
        List<SysApiNode> sysApiNodeList = sysApiService.getApiTreeById(apiNameLike, apiStatus);
        return R.ok().data(sysApiNodeList);
    }

    @PostMapping("add")
    public R add (@RequestBody SysApi sysApi) {
        sysApiService.addApi(sysApi);
        return R.ok();
    }

    @PostMapping("update")
    public R update(@RequestBody SysApi sysApi) {
        sysApiService.updateApi(sysApi);
        return R.ok();
    }

    @PostMapping("delete")
    public R delete(@RequestBody SysApi sysApi) {
        sysApiService.delete(sysApi);
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
        // 全部menu的tree结构
        result.put("tree", sysApiService.getApiTreeById("", null));
        // 获得所有展开的id（level为2）
        result.put("expandedKeys", sysApiService.getExpandedKeys());
        // 获取所有有权限的menu的id
        result.put("checkedKeys", sysApiService.getCheckedKeys(roleId));
        return R.ok().data(result);
    }

    @PostMapping("savekeys")
    public R saveKeys(@RequestBody RoleCheckedIds roleCheckedIds) {
        sysApiService.saveCheckedKeys(roleCheckedIds.getRoleId(), roleCheckedIds.getCheckedIds());
        return R.ok();
    }
}
