package com.thc.serverjwt.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thc.serverjwt.entity.SysApi;
import com.thc.serverjwt.entity.SysMenu;
import com.thc.serverjwt.entity.SysOrg;
import com.thc.serverjwt.entity.SysUserOrg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.mapper
 * @Description:
 * @date 2020/10/2 6:45 下午
 */
@Repository
public interface SystemMapper {

    /**
     *
     * @param rootOrgId 上级组织编码, 查找所有父节点包含rootorgid的结点
     * @param orgNameLike 组织名称模糊查询
     * @param orgStatus 组织机构是否处于禁用状态查询
     * @return
     */
    List<SysOrg> selectOrgTree(@Param("rootOrgId") Integer rootOrgId ,
                               @Param("orgNameLike") String orgNameLike,
                               @Param("orgStatus") Boolean orgStatus);

    List<SysMenu> selectMenuTree(@Param("rootOrgId") Integer rootMenuId,
                                 @Param("menuNameLike") String menuNameLike,
                                 @Param("menuStatus") Boolean menuStatus);

    List<SysApi> selectApiTree(@Param("rootApiId") Integer rootApiId,
                               @Param("apiNameLike") String apiNameLike,
                               @Param("apiStatus") Boolean apiStatus);

    /**
     * 查找所有level为2的api的id
     * @return
     */
    List<Integer> selectApiExpandedKeys();

    /**
     * 查找所有level为2的menu的id
     * @return
     */
    List<Integer> selectMenuExpandedKeys();

    /**
     * 根据roleId查询所有有权限的api的id
     * @param roleId roleId
     * @return
     */
    List<Integer> selectApiCheckedKeys(Integer roleId);

    /**
     * 根据roleId查询所有有权限的menu的id
     * @param roleId
     * @return
     */
    List<Integer> selectMenuCheckedKeys(Integer roleId);

    /**
     * 根据roleId插入数据进role_menu表，设置role的menu权限
     * @param roleId roleId
     * @param checkedIds 所有赋予权限的menu的id
     * @return
     */
    Integer insertRoleMenuIds(@Param("roleId") Integer roleId,
                              @Param("checkedIds") List<Integer> checkedIds);

    /**
     * 根据roleId插入数据进role_api表，设置role的api权限
     * @param roleId
     * @param checkedIds
     * @return
     */
    Integer insertRoleApiIds(@Param("roleId") Integer roleId,
                             @Param("checkedIds") List<Integer> checkedIds);

    /**
     * 查询用户信息
     * @param orgId
     * @param username
     * @param phone
     * @param email
     * @param enabled
     * @param createStartTime 时间查询范围
     * @param createEndTime 时间查询范围
     * @return
     */
    Page<SysUserOrg> selectUser(Page<SysUserOrg> page,
                                @Param("orgId") Integer orgId ,
                                @Param("username") String username ,
                                @Param("phone") String phone,
                                @Param("email") String email,
                                @Param("enabled") Boolean enabled,
                                @Param("createStartTime") Date createStartTime,
                                @Param("createEndTime") Date createEndTime);

    //根据用户id获取用户已经具有的角色
    List<Integer> getCheckedRoleIds(Integer userId);
    //插入用户id、角色id关系(保存)
    Integer insertUserRoleIds(@Param("userId") Integer userId,
                              @Param("checkedIds") List<Integer> checkedIds);

    // 根据username查询menu
    List<SysMenu> selectMenuByUsername(@Param("username") String username );
}
