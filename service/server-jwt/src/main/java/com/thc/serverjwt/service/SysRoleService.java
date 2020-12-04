package com.thc.serverjwt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thc.serverjwt.entity.SysRole;
import com.thc.serverjwt.entity.SysUserRole;
import com.thc.serverjwt.mapper.SysRoleApiMapper;
import com.thc.serverjwt.mapper.SysRoleMapper;
import com.thc.serverjwt.mapper.SysUserRoleMapper;
import com.thc.serverjwt.mapper.SystemMapper;
import com.thc.servicebase.exceptionhandler.MyException;
import com.thc.servicebase.exceptionhandler.MyExceptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.service
 * @Description:
 * @date 2020/10/8 7:59 下午
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    /**
     * 查询
     * @param roleLike 模糊查询条件 查三个
     * @return
     */
    public List<SysRole> queryRoles(String roleLike) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.like("role_code", roleLike).or().like("role_name", roleLike).or().like("role_desc", roleLike);
        List<SysRole> roleList = roleMapper.selectList(wrapper);
        return roleList;
    }

    // 改
    public void updateRole(SysRole sysRole) {
        if (sysRole.getId()==null) {
            throw new MyException(MyExceptionType.USER_INPUT_ERROR.getCode(), "id不能为空");
        }
        // TODO update是否有检查不存在的修改情况 没有需要增加判断
        roleMapper.updateById(sysRole);
    }

    public void addRole(SysRole sysRole) {
        // TODO 同上
        // 判断传入的code是否是以ROLE开头, 不是需要加上
        if (!sysRole.getRoleCode().startsWith("ROLE_")) {
            sysRole.setRoleCode("ROLE_"+sysRole.getRoleCode());
        }
        roleMapper.insert(sysRole);
    }

    public void deleteRole(SysRole sysRole) {
        roleMapper.deleteById(sysRole.getId());
    }

    public Map<String,Object> getRolesAndChecked(Integer userId){
        Map<String,Object> ret = new HashMap<>();
        if(userId == null){
            throw new MyException(MyExceptionType.USER_INPUT_ERROR.getCode(),
                    "获取角色信息必需要：用户id参数");
        }else{
            //获取已具有的角色id列表
            ret.put("checkedRoleIds",systemMapper.getCheckedRoleIds(userId));
            //系统内所有角色列表
            ret.put("roleDatas", roleMapper.selectList(null));
        }
        return ret;
    }


    @Transactional
    public void saveCheckedKeys(Integer userId,List<Integer> checkedIds){
        //保存用户id、角色id关系之前，先根据用户id删除sys_user_role关系数据
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        userRoleMapper.delete(wrapper);
        //保存用户id、角色id关系(一个用户userId，多个角色List<Integer> checkedIds)
        systemMapper.insertUserRoleIds(userId,checkedIds);
    }
}
