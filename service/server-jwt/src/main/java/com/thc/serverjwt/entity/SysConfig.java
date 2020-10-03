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
 * 系统全局配置参数
 * </p>
 *
 * @author thc
 * @since 2020-10-01
 */
@Data
@TableName("sys_config")
@ApiModel(value="SysConfig对象", description="系统全局配置参数")
public class SysConfig implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "参数名称(中文)")
    private String paramName;

    @ApiModelProperty(value = "参数编码唯一标识(英文及数字)")
    private String paramKey;

    @ApiModelProperty(value = "参数值")
    private String paramValue;

    @ApiModelProperty(value = "参数描述备注")
    private String paramDesc;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
