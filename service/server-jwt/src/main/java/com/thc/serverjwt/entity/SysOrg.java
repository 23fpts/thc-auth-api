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
 * 系统组织结构表
 * </p>
 *
 * @author thc
 * @since 2020-10-01
 */
@Data
@TableName("sys_org")
@ApiModel(value="SysOrg对象", description="系统组织结构表")
public class SysOrg implements Serializable{

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "上级组织编码")
    private Integer orgPid;

    @ApiModelProperty(value = "所有的父节点id")
    private String orgPids;

    @ApiModelProperty(value = "0:不是叶子节点，1:是叶子节点")
    @TableField("is_leaf")
    private Boolean leaf;

    @ApiModelProperty(value = "组织名")
    private String orgName;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "邮件")
    private String email;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "组织层级")
    private Integer level;

    @ApiModelProperty(value = "是否禁用，0:启用(否）,1:禁用(是)")
    private Boolean status;


}
