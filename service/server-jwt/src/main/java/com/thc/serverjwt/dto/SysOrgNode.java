package com.thc.serverjwt.dto;

import com.thc.serverjwt.entity.SysOrg;
import com.thc.servicebase.utils.tree.DataTree;

import java.util.List;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.dto
 * @Description:
 * @date 2020/10/2 7:24 下午
 */
public class SysOrgNode extends SysOrg implements DataTree<SysOrgNode> {

    //为某对象加上children成员变量
    private List<SysOrgNode> children;

    @Override
    public Integer getParentId() {
        //因为不同的人设计model或者数据库，
        //父id字段叫不同的名字，pid或parentId或org_pid等，
        //这里适配一下，统一为使用getParentId，获取父id数据
        return super.getOrgPid();
    }

    //set和get方法，为children赋值取值
    @Override
    public void setChildren(List<SysOrgNode> children) {
        this.children = children;
    }

    @Override
    public List<SysOrgNode> getChildren() {
        return this.children;
    }
}
