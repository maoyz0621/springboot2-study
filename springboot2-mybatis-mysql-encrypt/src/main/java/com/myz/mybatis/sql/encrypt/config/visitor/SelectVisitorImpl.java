/**
 * Copyright 2023 Inc.
 **/
package com.myz.mybatis.sql.encrypt.config.visitor;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import org.apache.ibatis.mapping.BoundSql;

import java.util.List;

/**
 * @author maoyz0621 on 2023/5/29
 * @version v1.0
 */
@Slf4j
public class SelectVisitorImpl extends SelectVisitorAdapter {

    private BoundSql boundSql;

    public SelectVisitorImpl(BoundSql boundSql) {
        this.boundSql = boundSql;
    }

    @Override
    public void visit(PlainSelect plainSelect) {
        // select 字段
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        // select ... from 表名
        FromItem fromItem = plainSelect.getFromItem();
        // where条件
        Expression where = plainSelect.getWhere();
        log.info("select items={},fromDb={},whereCond={}", selectItems, fromItem, where);

    }
}