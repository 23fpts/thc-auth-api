package com.thc.serverjwt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thc.serverjwt.dto.SysOrgNode;
import com.thc.serverjwt.entity.SysOrg;
import com.thc.serverjwt.mapper.SysOrgMapper;
import com.thc.serverjwt.mapper.SystemMapper;
import com.thc.servicebase.exceptionhandler.MyException;
import com.thc.servicebase.exceptionhandler.MyExceptionType;
import com.thc.servicebase.utils.tree.DataTreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private SysOrgMapper sysOrgMapper;

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

    /**
     * 新增
     * @param sysOrg 新增
     */
    @Transactional
    public void addOrg(SysOrg sysOrg) {
        setOrgIdsAndLevel(sysOrg);
        sysOrg.setLeaf(true); //新增的组织节点都是子节点，没有下级
        SysOrg parent = new SysOrg();
        parent.setId(sysOrg.getOrgPid());
        parent.setLeaf(false); // 更新父结点不为叶子结点
        sysOrgMapper.updateById(parent);
        sysOrg.setStatus(false); // 默认没有被禁用
        sysOrgMapper.insert(sysOrg);

    }

    /**
     * 删除
     * @param sysOrg
     */
    @Transactional
    public void deleteOrg(SysOrg sysOrg){
        // 查找欲删除结点sysOrg的子节点
        QueryWrapper<SysOrg> wrapper = new QueryWrapper<>();
        wrapper.like("org_pids", "["+ sysOrg.getId()+"]");
        List<SysOrg> myChildren = sysOrgMapper.selectList(wrapper);
        // 存在子结点，不允许删除
        if (myChildren.size()>0) {
            throw new MyException(MyExceptionType.USER_INPUT_ERROR.getCode(), "不能删除有下级组织的组织机构");
        }
        // 删除
        // 查找所有父节点和欲删除结点sysOrg父节点相同的结点
        QueryWrapper<SysOrg> wrapperParent = new QueryWrapper<>();
        wrapperParent.like("org_pids", "["+sysOrg.getOrgPid()+"]");
        List<SysOrg> myFatherChildren = sysOrgMapper.selectList(wrapperParent);
        Integer myFatherChildrenSize = sysOrgMapper.selectCount(wrapperParent);
        // 只有sysOrg一个孩子，设为叶子结点
        if (myFatherChildrenSize==1) {
            SysOrg parent = new SysOrg();
            parent.setId(sysOrg.getOrgPid());
            parent.setLeaf(true); // 父节点为叶子结点
            // 更新
            sysOrgMapper.updateById(parent);
        }
        // 删除结点
        sysOrgMapper.deleteById(sysOrg);
    }

    public void updateOrg(SysOrg sysOrg) {
        if (sysOrg.getId() == null) {
            throw new MyException(MyExceptionType.USER_INPUT_ERROR.getCode(), "id为空");
        }
        //
        if (sysOrgMapper.selectById(sysOrg.getId())==null) {
            throw new MyException(MyExceptionType.USER_INPUT_ERROR.getCode(), "不存在");
        }
        sysOrgMapper.updateById(sysOrg);
    }

    /**
     * 设置某子节点的所有祖辈id
     * @param child child
     */
    private void setOrgIdsAndLevel(SysOrg child){
        List<SysOrg> allOrgs = sysOrgMapper.selectList(null);
        for(SysOrg sysOrg: allOrgs){
            // 从组织列表中找到自己的直接父亲
            if(sysOrg.getId().equals(child.getOrgPid())){
                // 直接父亲的所有祖辈id + 直接父id = 当前子节点的所有祖辈id
                // 爸爸的所有祖辈 + 爸爸 = 孩子的所有祖辈
                child.setOrgPids(sysOrg.getOrgPids() + ",[" + child.getOrgPid() + "]" );
                child.setLevel(sysOrg.getLevel() + 1);
            }
        }
    }


}
