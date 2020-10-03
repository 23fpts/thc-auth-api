package com.thc.serverjwt.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 系统Http接口表，配合sys_role_api控制接口访问权限
 * </p>
 *
 * @author thc
 * @since 2020-10-01
 */
@Data
@TableName("sys_api")
@ApiModel(value="SysApi对象", description="系统Http接口表，配合sys_role_api控制接口访问权限")
public class SysApi implements Serializable{

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "接口父ID(即接口分组)")
    private Integer apiPid;

    @ApiModelProperty(value = "当前接口的所有上级id(即所有上级分组)")
    private String apiPids;

    @ApiModelProperty(value = "0:不是叶子节点，1:是叶子节点")
    @TableField("is_leaf")
    private Boolean leaf;

    @ApiModelProperty(value = "接口名称")
    private String apiName;

    @ApiModelProperty(value = "跳转URL")
    private String url;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "层级，1：接口分组，2：接口")
    private Integer level;

    @ApiModelProperty(value = "是否禁用，0:启用(否）,1:禁用(是)")
    private Boolean status;

}
