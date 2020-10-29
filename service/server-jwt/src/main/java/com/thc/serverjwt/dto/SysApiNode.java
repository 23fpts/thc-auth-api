package com.thc.serverjwt.dto;

import com.thc.serverjwt.entity.SysApi;
import com.thc.servicebase.utils.tree.DataTree;

import java.util.List;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.dto
 * @Description:
 * @date 2020/10/29 4:26 下午
 */
public class SysApiNode extends SysApi implements DataTree<SysApiNode> {

    private List<SysApiNode> children;

    @Override
    public Integer getParentId() {
        return super.getApiPid();
    }

    @Override
    public void setChildren(List<SysApiNode> children) {
        this.children = children;
    }

    @Override
    public List<SysApiNode> getChildren() {
        return children;
    }
}
