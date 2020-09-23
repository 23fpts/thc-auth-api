package com.thc.jwt.auth;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author thc
 * @Title:
 * @Package com.thc.jwt.auth
 * @Description:
 * @date 2020/9/23 5:41 下午
 */
@Mapper
public interface MyUserDetailsServiceMapper {

    //根据userID查询用户信息
    @Select("SELECT username,password,enabled\n" +
            "FROM sys_user u\n" +
            "WHERE u.username = #{userId}")
    MyUserDetails findByUserName(@Param("userId") String userId);

    //根据userID查询用户角色
    @Select("SELECT role_code\n" +
            "FROM sys_role r\n" +
            "LEFT JOIN sys_user_role ur ON r.id = ur.role_id\n" +
            "LEFT JOIN sys_user u ON u.id = ur.user_id\n" +
            "WHERE u.username = #{userId}")
    List<String> findRoleByUserName(@Param("userId") String userId);


    //根据用户角色查询用户权限
    //foreach元素的属性主要有 item，index，collection，open，separator，close。
    //
    //    item表示集合中每一个元素进行迭代时的别名，
    //    index指 定一个名字，用于表示在迭代过程中，每次迭代到的位置，
    //    open表示该语句以什么开始，
    //    separator表示在每次进行迭代之间以什么符号作为分隔 符，
    //    close表示以什么结束。
    @Select({
            "<script>",
            "SELECT url " ,
            "FROM sys_menu m " ,
            "LEFT JOIN sys_role_menu rm ON m.id = rm.menu_id " ,
            "LEFT JOIN sys_role r ON r.id = rm.role_id ",
            "WHERE r.role_code IN ",
            "<foreach collection='roleCodes' item='roleCode' open='(' separator=',' close=')'> ",
            "#{roleCode}",
            "</foreach>",
            "</script>"
    })
    List<String> findAuthorityByRoleCodes(@Param("roleCodes") List<String> roleCodes);

}
