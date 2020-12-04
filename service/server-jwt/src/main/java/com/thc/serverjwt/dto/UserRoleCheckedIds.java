package com.thc.serverjwt.dto;

import lombok.Data;

import java.util.List;

/**
 * @author thc
 * @Title:
 * @Package com.thc.serverjwt.dto
 * @Description: 一个角色多个role
 * @date 2020/12/3 6:27 下午
 */
@Data
public class UserRoleCheckedIds {
    private Integer userId;
    private List<Integer> checkedIds;
}
