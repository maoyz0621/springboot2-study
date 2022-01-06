/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2.mybatisplus34.configuration.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.function.Supplier;

/**
 * 填充器
 *
 * @author maoyz0621 on 19-4-13
 * @version: v1.0
 */
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
        this.setFieldValByName("lastModifiedBy", "maoyz-update", metaObject);
        this.strictInsertFill(metaObject, "createdTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "lastModifiedTime", LocalDateTime.class, LocalDateTime.now());
        // todo 全局数据中获取 TtlThreadLocal
        this.strictInsertFill(metaObject, "tenantId", Long.class, 65231313678678678L);
        this.strictInsertFill(metaObject, "companyId", Long.class, 567843322111L);
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
        this.strictUpdateFill(metaObject, "lastModifiedTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public MetaObjectHandler strictFillStrategy(MetaObject metaObject, String fieldName, Supplier<?> fieldVal) {
        Object o = fieldVal.get();
        if (o != null) {
            metaObject.setValue(fieldName, o);
        }
        return this;
    }
}
