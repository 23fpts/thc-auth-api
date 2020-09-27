package com.thc.jwt.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author thc
 * @Title:
 * @Package com.thc.mapper
 * @Description:
 * @date 2020/9/27 7:46 下午
 */
@Component
public interface MyRBACServiceMapper {

    /**
     * 查询某个用户所具有的菜单访问权限
     * @param userId 用户名或手机号
     * @return 菜单权限列表
     */
    @Select("SELECT url\n" +
            "FROM sys_menu m\n" +
            "LEFT JOIN sys_role_menu rm ON m.id = rm.menu_id\n" +
            "LEFT JOIN sys_role r ON r.id = rm.role_id\n" +
            "LEFT JOIN sys_user_role ur ON r.id = ur.role_id\n" +
            "LEFT JOIN sys_user u ON u.id = ur.user_id\n" +
            "WHERE u.username = #{userId} or u.phone = #{userId}")
    List<String> findUrlsByUserName(@Param("userId") String userId);


}