package com.thc.serverjwt.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.entity
 * @Description:
 * @date 2020/11/26 10:11 下午
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserOrg extends SysUser {

    private String orgName;


}
