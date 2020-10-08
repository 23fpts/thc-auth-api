package com.thc.serverjwt.mapper;

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

    List<SysMenu> selectMenuTree(@Param("rootOrgId") Integer rootOrgId,
                                 @Param("menuNameLike") String menuNameLike,
                                 @Param("menuStatus") Boolean menuStatus);
}
