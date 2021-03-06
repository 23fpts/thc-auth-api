package com.thc.serverjwt.dto;

import lombok.Data;

import java.util.List;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.dto
 * @Description: 一个role多个权限
 * @date 2020/10/10 8:53 下午
 */
@Data
public class RoleCheckedIds {

    private Integer roleId;

    private List<Integer> checkedIds;
}
