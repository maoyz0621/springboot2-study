/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author maoyz0621 on 19-4-9
 * @version: v1.0
 */
@Data
public class BasePO {

    /**
     * 使用数据库的自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;
}
