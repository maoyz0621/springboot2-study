/**
 * Copyright 2023 Inc.
 **/
package com.myz.mybatis.sql.encrypt.config.visitor;

import com.myz.mybatis.sql.encrypt.utils.SqlParserUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.upsert.Upsert;
import org.apache.ibatis.mapping.BoundSql;

import java.util.List;

/**
 * @author maoyz0621 on 2023/5/29
 * @version v1.0
 */
@Slf4j
public class UpsertStatementVisitor extends StatementVisitorAdapter {

    private BoundSql boundSql;

    public UpsertStatementVisitor(BoundSql boundSql) {
        this.boundSql = boundSql;
    }


    @Override
    public void visit(Update update) {
        List<Column> columns = update.getColumns();
        Table table = update.getTable();
        Expression where = update.getWhere();

        log.info("update items={}, fromDb={}, whereCond={}", columns, table, where);
    }

    @Override
    public void visit(Insert insert) {
        List<Column> columns = insert.getColumns();
        Table table = insert.getTable();
        ExpressionList itemsList = (ExpressionList) insert.getItemsList();

        itemsList.accept(new SecurityItemsListVisitor(boundSql));
        log.info("insert items={}, fromDb={}, itemsList={}", columns, table, itemsList);
        // columnName
        String name = table.getName();
        for (Column column : columns) {
            String columnName = column.getColumnName();
            String newColumnName = columnName+"_mask";
            insert.addColumns(new Column[]{new Column(newColumnName)});
        }


        try {
            SqlParserUtils.dynamicModifySql(boundSql, insert.toString());
        } catch (Exception e) {

        }
    }

    @Override
    public void visit(Upsert upsert) {
        super.visit(upsert);
    }
}