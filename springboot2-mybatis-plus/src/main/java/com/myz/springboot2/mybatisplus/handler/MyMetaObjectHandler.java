/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 填充器
 *
 * @author maoyz0621 on 19-4-13
 * @version: v1.0
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyMetaObjectHandler.class);


    /**
     * 插入补充参数
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        logger.info("*********** MyMetaObjectHandler insertFill() ******************");
        // 避免使用metaObject.setValue()
        this.setFieldValByName("operator","maoyz-insert", metaObject);
        this.setInsertFieldValByName("create_at", new Date(), metaObject);
        this.setInsertFieldValByName("update_at", new Date(), metaObject);
    }

    /**
     * 更新补充参数
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        logger.info("*********** MyMetaObjectHandler updateFill() ******************");
        this.setFieldValByName("operator","maoyz-update",metaObject);
        this.setUpdateFieldValByName("update_at", new Date(), metaObject);
    }
}
