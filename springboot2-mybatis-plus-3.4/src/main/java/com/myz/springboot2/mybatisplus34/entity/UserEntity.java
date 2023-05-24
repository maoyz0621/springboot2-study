package com.myz.springboot2.mybatisplus34.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author maoyz
 * @since 2021-12-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
@Accessors(chain = true)
public class UserEntity extends BasePO implements Serializable {

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
