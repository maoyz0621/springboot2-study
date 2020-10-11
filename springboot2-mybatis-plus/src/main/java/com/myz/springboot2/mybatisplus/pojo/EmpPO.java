/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2.mybatisplus.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author maoyz0621 on 19-4-13
 * @version: v1.0
 */
@Data
@TableName("emp")
public class EmpPO extends BasePO {

    private String name;

    private String email;

    /**
     * 支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime
     * 整数类型下 newVersion = oldVersion + 1
     * newVersion 会回写到 entity 中
     * 仅支持 updateById(id) 与 update(entity, wrapper) 方法
     *
     * update tbl_user set name = 'update',version = 3 where id = 100 and version = 2
     */
    // @Version
    // private Integer version;


}
