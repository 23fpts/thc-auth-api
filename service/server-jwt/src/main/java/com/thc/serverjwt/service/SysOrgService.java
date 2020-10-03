package com.thc.serverjwt.service;

import com.thc.serverjwt.dto.SysOrgNode;
import com.thc.serverjwt.entity.SysOrg;
import com.thc.serverjwt.mapper.SystemMapper;
import com.thc.servicebase.exceptionhandler.MyException;
import com.thc.servicebase.exceptionhandler.MyExceptionType;
import com.thc.servicebase.utils.tree.DataTreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.service
 * @Description:
 * @date 2020/10/2 7:49 下午
 */
@Service
public class SysOrgService {

    @Autowired
    private SystemMapper systemMapper;

    /**
     *
     * @param rootOrgId 组织根id
     * @param orgNameLike 组织名模糊查询
     * @param orgStatus 组织状态
     * @return
     */
    public List<SysOrgNode> getOrgTreeById(Integer rootOrgId,
                                           String orgNameLike,
                                           Boolean orgStatus) {


        if (rootOrgId != null) {
            // 先获取乱序的所有结点
            List<SysOrg> sysOrgs = systemMapper.selectOrgTree(rootOrgId, orgNameLike, orgStatus);
            // 利用工具类进行把sysOrgs中的值赋值到sysOrgNodes中,
            List<SysOrgNode> sysOrgNodes = sysOrgs.stream().map(item -> {
                SysOrgNode bean = new SysOrgNode();
                BeanUtils.copyProperties(item, bean);
                return bean;
            }).collect(Collectors.toList());
            // 看是否有模糊查询，如果有模糊查询不能生成树形结构，直接就返回了，否则生成树形结构
            if (!StringUtils.isEmpty(orgNameLike) || !StringUtils.isEmpty(orgStatus)) {
                return sysOrgNodes;
            } else {
                return DataTreeUtil.buildTree(sysOrgNodes, rootOrgId);
            }

        } else {
            throw new MyException(MyExceptionType.USER_INPUT_ERROR.getCode(), "查询参数用户名组织id不能为空");
        }
    }
}
