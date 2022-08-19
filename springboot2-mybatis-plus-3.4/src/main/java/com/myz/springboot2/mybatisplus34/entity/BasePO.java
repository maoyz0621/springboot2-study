/**
 * Copyright 2022 Inc.
 **/
package com.myz.springboot2.mybatisplus34.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author maoyz0621 on 2022/1/4
 * @version v1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BasePO implements Serializable {

    private Long id;

    private String remarks;

    // @TableLogic
    private Integer enabled;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 租户ID
     */

    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;

    /**
     * 公司id
     */
    @TableField(fill = FieldFill.INSERT)
    private Long companyId;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 操作员
     */
    private String operator;

    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String lastModifiedBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastModifiedTime;
}