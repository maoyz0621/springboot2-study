/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
     * fieldName:属性名称，不是数据库中column
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        logger.info("*********** MyMetaObjectHandler insertFill() ******************");
        // 避免使用metaObject.setValue()
        this.setFieldValByName("createdBy", "maoyz-insert", metaObject);
        this.setFieldValByName("lastModifiedBy", "maoyz-insert", metaObject);
        this.setInsertFieldValByName("createdTime", LocalDateTime.now(), metaObject);
        this.setInsertFieldValByName("lastModifiedTime", LocalDateTime.now(), metaObject);
    }

    /**
     * 更新补充参数
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        logger.info("*********** MyMetaObjectHandler updateFill() ******************");
        this.setFieldValByName("lastModifiedBy", "maoyz-update", metaObject);
        this.setUpdateFieldValByName("lastModifiedTime", LocalDateTime.now(), metaObject);
    }
}
