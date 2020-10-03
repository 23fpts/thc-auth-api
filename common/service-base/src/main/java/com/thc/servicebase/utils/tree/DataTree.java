package com.thc.servicebase.utils.tree;

import java.util.List;

/**
 * @author thc
 * @Title:
 * @Package com.thc.servicebase.utils
 * @Description:
 * @date 2020/10/2 7:21 下午
 */
public interface DataTree<T> {
    //维护树形关系：元素一id
    public Integer getId();
    //维护树形关系：元素二父id
    public Integer getParentId();
    //子节点数组
    public void setChildren(List<T> children);

    public List<T> getChildren();
}
