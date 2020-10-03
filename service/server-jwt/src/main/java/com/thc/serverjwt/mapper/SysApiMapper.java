package com.thc.serverjwt.mapper;

import com.thc.serverjwt.entity.SysApi;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统Http接口表，配合sys_role_api控制接口访问权限 Mapper 接口
 * </p>
 *
 * @author thc
 * @since 2020-10-01
 */
@Repository
public interface SysApiMapper extends BaseMapper<SysApi> {

}
