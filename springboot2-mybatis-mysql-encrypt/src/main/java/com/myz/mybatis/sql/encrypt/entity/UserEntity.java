/**
 * Copyright 2023 Inc.
 **/
package com.myz.mybatis.sql.encrypt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author maoyz0621 on 2023/5/25
 * @version v1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
@Accessors(chain = true)
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 年龄1
     */
    @TableField("age_enum")
    private Integer ageEnum;

    /**
     * 性别,0:MALE, 1:FEMALE
     */
    private Integer gender;

    /**
     * 年级
     */
    private Integer grade;

    /**
     * 邮箱
     */
    private String email;
}