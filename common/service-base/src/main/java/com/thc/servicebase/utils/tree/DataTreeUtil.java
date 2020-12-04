package com.thc.servicebase.utils.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author thc
 * @Title:
 * @Package com.thc.servicebase.utils
 * @Description: 构建树形结构
 * @date 2020/10/2 7:33 下午
 */
public class DataTreeUtil {

    //构造无根树形结构数据，比如系统左侧菜单栏
    public static <ID,T extends DataTree<T>> List<T> buildTreeWithoutRoot(List<T> paramList, ID rootNodeId) {
        List<T> returnList = new ArrayList<T>();
        for (T node : paramList) {//查找根节点
            //从2级节点开始构造
            if (node.getParentId().equals(rootNodeId)) {
                returnList.add(node);
            }
        }
        for (T entry : paramList) {
            toTreeChildren(returnList, entry);
        }
        return returnList;
    }


    /**
     * 递归构建tree结构
     * @param paramList 未排成树形结构的所有结点列表
     * @param rootNodeId 跟结点id
     * @param <T> 类型
     * @return
     */
    public static <T extends DataTree<T>> List<T> buildTree (
            List<T> paramList, Integer rootNodeId) {
        List<T> returnList = new ArrayList<T>();
        for (T node : paramList) { // 查找根节点
            if(node.getId().equals(rootNodeId) ){
                returnList.add(node);
            }
        }
        // 递归为children赋值 确定paramList中每个node在tree中的位置
        for (T entry : paramList) {
            toTreeChildren(returnList, entry);
        }
        return returnList;
    }

    private static <T extends DataTree<T>> void toTreeChildren (List<T> returnList, T entry) {
        for (T node : returnList) {
            // 找到了当前需要定位的结点entry的父节点node
            if (entry.getParentId().equals(node.getId())) {
                // 如果需要定位的结点entry的父节点node目前没有子节点就新建
                if (node.getChildren() == null) {
                    node.setChildren(new ArrayList<T>());
                }
                // 然后把需要定位的entry加入到子节点中
                node.getChildren().add(entry);
            }
            // 找父节点node的时候，不是node的子结点，则在父节点的子节点node.getChildren()们中继续找
            if (node.getChildren() != null) {
                toTreeChildren(node.getChildren(),entry);
            }
        }
    }
}
