package com.thc.serverjwt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thc.serverjwt.dto.SysMenuNode;
import com.thc.serverjwt.entity.SysMenu;
import com.thc.serverjwt.entity.SysRoleMenu;
import com.thc.serverjwt.mapper.SysMenuMapper;
import com.thc.serverjwt.mapper.SysRoleMenuMapper;
import com.thc.serverjwt.mapper.SystemMapper;
import com.thc.servicebase.exceptionhandler.MyException;
import com.thc.servicebase.exceptionhandler.MyExceptionType;
import com.thc.servicebase.utils.tree.DataTreeUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.service
 * @Description:
 * @date 2020/10/5 3:19 下午
 */
@Service
public class SysMenuService {

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 生成树形结构
     * @param menuNameLike 名字模糊查询
     * @param menuStatus 状态查询
     * @return
     */
    public List<SysMenuNode> getMenuTreeById(String menuNameLike,
                                             Boolean menuStatus) {
        // menu的根id为1
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("id", 1);
        SysMenu sysMenu = sysMenuMapper.selectOne(wrapper);
        if (sysMenu!=null){
            Integer rootMenuId = sysMenu.getId();
            List<SysMenu> sysMenuList = systemMapper.selectMenuTree(rootMenuId, menuNameLike, menuStatus);
            List<SysMenuNode> sysMenuNodeList = sysMenuList.stream().map(item -> {
                SysMenuNode sysMenuNode = new SysMenuNode();
                BeanUtils.copyProperties(item, sysMenuNode);
                return sysMenuNode;
            }).collect(Collectors.toList());
            // 如果穿了查询条件不进行树形结构排列
            if (!StringUtils.isEmpty(menuNameLike) || !StringUtils.isEmpty(menuStatus)) {
                return sysMenuNodeList;
            } else {
                // 构建树形结构
                return DataTreeUtil.buildTree(sysMenuNodeList, rootMenuId);
            }
        } else {
            throw new MyException(MyExceptionType.USER_INPUT_ERROR.getCode(), "查询参数用户名组织id不能为空");
        }
    }

    /**
     * 改
     * @param sysMenu
     */
    public void updateMenu(SysMenu sysMenu) {
        if (sysMenu.getId()==null) {
            throw new MyException(MyExceptionType.USER_INPUT_ERROR.getCode(), "id无输入");
        }
        sysMenuMapper.updateById(sysMenu);
    }

    /**
     * 新增
     * @param sysMenu
     */
    @Transactional
    public void addMenu(SysMenu sysMenu) {
        setMenuPidsAndLevel(sysMenu);
        sysMenu.setLeaf(true);
        sysMenu.setStatus(false);
        SysMenu parent = new SysMenu();
        parent.setId(sysMenu.getMenuPid());
        parent.setLeaf(false);
        sysMenuMapper.updateById(parent);
        sysMenuMapper.insert(sysMenu);
    }

    @Transactional
    public void delete(SysMenu sysMenu) {
        // 查找看删除的结点是否有子节点，如果有不允许删除 , TODO 用leaf字段判断也可以
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.like("menu_pids", "["+sysMenu.getId()+"]");
        Integer myChildrenCount = sysMenuMapper.selectCount(wrapper);
        if (myChildrenCount>0) {
            throw new MyException(MyExceptionType.SYSTEM_ERROR.getCode(), "存在子节点，不能删除");
        }
        // 删除，如果父亲结点除了sysMenu没有其他子节点，则父亲结点leaf设为true
        QueryWrapper<SysMenu> wrapperParent = new QueryWrapper<>();
        wrapperParent.like("menu_pids", "["+sysMenu.getMenuPid()+"]");
        Integer myParentChildCount = sysMenuMapper.selectCount(wrapperParent);
        if (myParentChildCount==1) {
            SysMenu parent = new SysMenu();
            parent.setId(sysMenu.getMenuPid());
            parent.setLeaf(true);
            sysMenuMapper.updateById(parent);
        }
        // 删除sysMenu
        sysMenuMapper.deleteById(sysMenu.getId());
    }

    /**
     * getCheckedKeys 获取某角色具有的菜单权限id列表
     * @param roleId roleId
     * @return
     */
    public List<Integer> getCheckedKeys(Integer roleId) {
        return systemMapper.selectMenuCheckedKeys(roleId);
    }

    /**
     * 获取所有level为2的结点的列表（获取菜单树默认展开节点）
     * @return
     */
    public List<Integer> getExpandedKeys() {
        return systemMapper.selectMenuExpandedKeys();
    }

    /**
     * 保存某角色roleId的勾选菜单权限id列表
     * @param roleId roleId
     * @param checkedIds menu权限id列表
     */
    @Transactional
    public void saveCheckedKeys(Integer roleId, List<Integer> checkedIds) {
        // 全删了在插入，前端每次必须把原来的数据加上新的传来
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        sysRoleMenuMapper.delete(wrapper);
        // 在插入所有的数据
        systemMapper.insertRoleMenuIds(roleId, checkedIds);

    }

    private void setMenuPidsAndLevel(SysMenu child) {
        // 找parent
        List<SysMenu> allMenus = sysMenuMapper.selectList(null);
        for (SysMenu menu : allMenus) {
            if (menu.getId().equals(child.getMenuPid())) {
                child.setMenuPids(menu.getMenuPids()+",["+child.getMenuPid()+"]");
                child.setLevel(menu.getLevel()+1);
            }
        }
    }

    // 根据用户的username获取他有权限的menu
    public List<SysMenuNode> getMenuTreeByUsername(String username) {
        //根据用户名查询该用户可以访问的菜单
        List<SysMenu> sysMenus = systemMapper.selectMenuByUsername(username);

        if (sysMenus.size() > 0) {
            Integer rootMenuId = sysMenus.get(0).getId(); //第一条记录为根节点
            //将List<SysMenu>转成List<SysMenuNode>
            List<SysMenuNode> sysMenuNodes =
                    sysMenus.stream().map(item -> {
                        SysMenuNode bean = new SysMenuNode();
                        BeanUtils.copyProperties(item, bean);
                        return bean;
                    }).collect(Collectors.toList());
            //构建树形结构节点列表
            return DataTreeUtil.buildTreeWithoutRoot(sysMenuNodes, rootMenuId);
        }
        return new ArrayList<>();
    }

}
