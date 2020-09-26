package com.thc.jwt.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * @author thc
 * @Title:
 * @Package com.thc.jwt.auth
 * @Description:
 * @date 2020/9/23 11:47 下午
 */
@Mapper
public interface MyRBACServiceMapper {

    /**
     * 根据username查询url
     * @param username
     * @return
     */
    @Select("select url \n" +
            "from sys_menu m\n" +
            "left join sys_role_menu rm on m.id = rm.menu_id\n" +
            "left join sys_role r on r.id = rm.role_id\n" +
            "left join sys_user_role ur on r.id = ur.role_id\n" +
            "left join sys_user u on u.id = ur.user_id\n" +
            "where u.username = #{username}")
    List<String> findUrlsByUsername(@Param("username") String username);
}
