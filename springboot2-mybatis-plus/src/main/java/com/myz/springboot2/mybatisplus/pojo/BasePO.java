/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author maoyz0621 on 19-4-9
 * @version v1.0
 */
@Data
public class BasePO implements Serializable {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建人
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 上一次更新人
     */
    @TableField(value = "last_modified_by", fill = FieldFill.INSERT_UPDATE)
    private String lastModifiedBy;

    /**
     * 上一次更新时间
     */
    @TableField(value = "last_modified_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastModifiedTime;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;

    /**
     * 是否可用，用来做逻辑删除 1:是 0否
     */
    @TableLogic
    @TableField(value = "enabled", fill = FieldFill.INSERT, condition = "1")
    private Integer enabled;

    /**
     * 版本号，用来做乐观锁
     */
    @Version
    @TableField(value = "version")
    private Integer version;

    /**
     * 禁用时间
     */
    // @TableField(value = "disabled_time", fill = FieldFill.UPDATE)
    // private LocalDateTime disabledTime;

}
