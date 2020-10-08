package com.thc.serverjwt.dto;

import com.thc.serverjwt.entity.SysMenu;
import com.thc.servicebase.utils.tree.DataTree;

import java.util.List;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.dto
 * @Description:
 * @date 2020/10/5 3:32 下午
 */
public class SysMenuNode extends SysMenu implements DataTree<SysMenuNode> {

    //为某对象加上children成员变量
    private List<SysMenuNode> children;

    @Override
    public Integer getParentId() {
        return super.getMenuPid();
    }

    @Override
    public void setChildren(List<SysMenuNode> children) {
        this.children = children;
    }

    @Override
    public List<SysMenuNode> getChildren() {
        return this.children;
    }
}
