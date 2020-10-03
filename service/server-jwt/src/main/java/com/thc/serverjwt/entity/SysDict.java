package com.thc.serverjwt.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据字典表
 * </p>
 *
 * @author thc
 * @since 2020-10-01
 */
@Data
@TableName("sys_dict")
@ApiModel(value="SysDict对象", description="数据字典表")
public class SysDict {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @ApiModelProperty(value = "分组编码")
    private String groupCode;

    @ApiModelProperty(value = "字典项名称")
    private String itemName;

    @ApiModelProperty(value = "字典项Value")
    private String itemValue;

    @ApiModelProperty(value = "字典项描述")
    private String itemDesc;

    @ApiModelProperty(value = "字典项创建时间")
    private Date createTime;


}
