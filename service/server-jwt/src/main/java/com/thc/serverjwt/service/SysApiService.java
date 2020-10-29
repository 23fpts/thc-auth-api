package com.thc.serverjwt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thc.serverjwt.dto.SysApiNode;
import com.thc.serverjwt.entity.SysApi;
import com.thc.serverjwt.entity.SysMenu;
import com.thc.serverjwt.entity.SysRoleApi;
import com.thc.serverjwt.entity.SysRoleMenu;
import com.thc.serverjwt.mapper.SysApiMapper;
import com.thc.serverjwt.mapper.SysRoleApiMapper;
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

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.service
 * @Description:
 * @date 2020/10/29 4:30 下午
 */
@Service
public class SysApiService {

    @Autowired
    private SysApiMapper sysApiMapper;

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private SysRoleApiMapper sysRoleApiMapper;

    /**
     * 生成树形结构
     * @param apiNameLike
     * @param apiStatus
     * @return
     */
    public List<SysApiNode> getApiTreeById(String apiNameLike, Boolean apiStatus) {
        // api的根id为1
        QueryWrapper<SysApi> wrapper = new QueryWrapper<>();
        wrapper.eq("id", 1);
        SysApi sysApi = sysApiMapper.selectOne(wrapper);
        if (sysApi!=null) {
            Integer rootApiId = sysApi.getId();
            List<SysApi> sysApiList = systemMapper.selectApiTree(rootApiId, apiNameLike, apiStatus);
            List<SysApiNode> sysApiNodeList = sysApiList.stream().map(item->{
                SysApiNode sysApiNode = new SysApiNode();
                BeanUtils.copyProperties(item, sysApiNode);
                return sysApiNode;
            }).collect(Collectors.toList());
            // 如果传了参数直接返回，不生成树形结构
            if (!StringUtils.isEmpty(apiNameLike) || !StringUtils.isEmpty(apiStatus)) {
                return sysApiNodeList;
            } else {
                // 构建树形结构
                return DataTreeUtil.buildTree(sysApiNodeList, rootApiId);
            }
        } else {
            throw new MyException(MyExceptionType.USER_INPUT_ERROR.getCode(), "查询参数用户名组织id不能为空");
        }
    }

    /**
     * 改
     * @param sysApi
     */
    @Transactional
    public void updateApi(SysApi sysApi) {
        if (sysApi.getId()==null) {
            throw new MyException(MyExceptionType.USER_INPUT_ERROR.getCode(), "id无输入");
        }
        sysApiMapper.updateById(sysApi);
    }

    /**
     * 新增
     * @param sysApi
     */
    @Transactional
    public void addApi(SysApi sysApi) {
        setApiPidsAndLevel(sysApi);
        sysApi.setLeaf(true);
        sysApi.setStatus(false);
        SysApi parent = new SysApi();
        parent.setId(sysApi.getApiPid());
        parent.setLeaf(false);
        sysApiMapper.updateById(parent);
        sysApiMapper.insert(sysApi);
    }

    /**
     * 删除
     * @param sysApi
     */
    @Transactional
    public void delete(SysApi sysApi) {
        // 查找是否有子节点，有的话，则不能删除
        QueryWrapper<SysApi> wrapper = new QueryWrapper<>();
        wrapper.like("api_pids", "["+sysApi.getId()+"]");
        Integer myChildrenCount = sysApiMapper.selectCount(wrapper);
        if (myChildrenCount>0) {
            throw new MyException(MyExceptionType.SYSTEM_ERROR.getCode(), "存在子节点，不能删除");
        }
        // 删除 如果父亲结点除了sysApi没有其他子节点，则父亲结点leaf设为true
        QueryWrapper<SysApi> wrapperParent = new QueryWrapper<>();
        wrapperParent.like("api_pids", "["+sysApi.getApiPid()+"]");
        Integer myParentChildrenCount = sysApiMapper.selectCount(wrapperParent);
        if (myChildrenCount==1) {
            SysApi sysApiParent = new SysApi();
            sysApiParent.setId(sysApi.getApiPid());
            sysApiParent.setLeaf(true);
            sysApiMapper.updateById(sysApiParent);
        }
        sysApiMapper.deleteById(sysApi);
    }

    /**
     * getCheckedKeys 获取某角色具有的菜单权限id列表
     * @param roleId roleId
     * @return
     */
    public List<Integer> getCheckedKeys(Integer roleId) {
        return systemMapper.selectApiCheckedKeys(roleId);
    }

    /**
     * 获取所有level为2的结点的列表（获取菜单树默认展开节点）
     * @return
     */
    public List<Integer> getExpandedKeys() {
        return systemMapper.selectApiExpandedKeys();
    }

    /**
     * 保存某角色roleId的勾选菜单权限id列表
     * @param roleId roleId
     * @param checkedIds api权限id列表
     */
    @Transactional
    public void saveCheckedKeys(Integer roleId, List<Integer> checkedIds) {
        // 全删了在插入，前端每次必须把原来的数据加上新的传来
        QueryWrapper<SysRoleApi> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        sysRoleApiMapper.delete(wrapper);
        // 在插入所有的数据
        systemMapper.insertRoleApiIds(roleId, checkedIds);
    }

    private void setApiPidsAndLevel(SysApi child) {
        // 找parent
        List<SysApi> allApis = sysApiMapper.selectList(null);
        for (SysApi api : allApis) {
            if (api.getId().equals(child.getApiPid())) {
                child.setApiPids(api.getApiPids()+",["+child.getApiPid()+"]");
                child.setLevel(api.getLevel()+1);
            }
        }
    }


}
