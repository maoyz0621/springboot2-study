/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2.mybatisplus.pojo;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.myz.springboot2.mybatisplus.enums.AgeEnum;
import com.myz.springboot2.mybatisplus.enums.GenderEnum;
import com.myz.springboot2.mybatisplus.enums.GradeEnum;
import lombok.Data;

/**
 * 注意跟sql表名一致@TableName()
 *
 * @author maoyz0621 on 19-4-9
 * @version: v1.0
 */
@Data
@TableName("user")
public class UserPO extends BasePO {

    private String name;

    private String email;

    /**
     * IEnum接口
     */
    private Integer age;

    /**
     * IEnum接口
     */
    @TableField("ageEnum")
    private AgeEnum ageEnum;

    /**
     * 使用注解@EnumValue
     */
    private GradeEnum grade;

    /**
     * 原生枚举： 默认使用枚举值顺序： 0：MALE， 1：FEMALE
     */
    private GenderEnum gender;

    /**
     * 逻辑删除,使用注解@TableLogic
     * logic-delete-value: 1 # 逻辑已删除值(默认为 1)
     * logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)
     */
    // @TableLogic
    private Integer isDelete;

    /**
     * 生成器策略部分
     * <p>
     * 1 字段必须声明TableField注解，属性fill选择对应策略，该申明告知 Mybatis-Plus 需要预留注入 SQL 字段
     * 2 填充处理器 MetaObjectHandler
     * 3 必须使用父类的setFieldValByName()或者setInsertFieldValByName/setUpdateFieldValByName方法，否则不会根据注解FieldFill.xxx来区分
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String operator;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
