package com.thc.jwt.auth;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author thc
 * @Title:
 * @Package com.thc.jwt.auth
 * @Description:
 * @date 2020/9/23 11:10 下午
 */
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    MyUserDetailsServiceMapper myUserDetailsServiceMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 加载基础用户信息
        MyUserDetails myUserDetails = myUserDetailsServiceMapper.findByUserName(username);

        // 加载用户角色列表
        List<String> roleCodes = myUserDetailsServiceMapper.findRoleByUserName(username);

        // 角色是一个特殊的权限，ROLE_ 前缀必须有, 如果数据库没有加需要重构
        // roleCodes = roleCodes.stream().map(rc -> "ROLE_"+rc).collect(Collectors.toList());

        // 通过用户角色列表加载用户的资源权限列表
        List<String> authorities = myUserDetailsServiceMapper.findAuthorityByRoleCodes(roleCodes);

        // 角色是一种特殊的权限，所以合并
        authorities.addAll(roleCodes);

        // 转成用逗号分隔的字符串，为用户设置权限标识
        myUserDetails.setAuthorities(
                AuthorityUtils.commaSeparatedStringToAuthorityList(
                        String.join(",", authorities)
                )
        );

        return null;
    }
}
