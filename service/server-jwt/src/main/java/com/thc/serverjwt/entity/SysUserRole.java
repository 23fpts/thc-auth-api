package com.thc.serverjwt.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户角色关系表
 * </p>
 *
 * @author thc
 * @since 2020-10-01
 */
@Data
@TableName("sys_user_role")
@ApiModel(value="SysUserRole对象", description="用户角色关系表")
public class SysUserRole  implements Serializable{

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "角色自增id")
    private Integer roleId;

    @ApiModelProperty(value = "用户自增id")
    private Integer userId;


}
