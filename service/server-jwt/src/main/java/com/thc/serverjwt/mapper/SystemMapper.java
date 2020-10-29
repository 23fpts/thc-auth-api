package com.thc.serverjwt.mapper;

import com.thc.serverjwt.entity.SysApi;
import com.thc.serverjwt.entity.SysMenu;
import com.thc.serverjwt.entity.SysOrg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
}
