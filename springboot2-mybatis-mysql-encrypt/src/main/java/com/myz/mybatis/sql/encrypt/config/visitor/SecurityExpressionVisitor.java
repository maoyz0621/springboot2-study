/**
 * Copyright 2023 Inc.
 **/
package com.myz.mybatis.sql.encrypt.config.visitor;

import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.JdbcParameter;

/**
 * @author maoyz0621 on 2023/6/29
 * @version v1.0
 */
public class SecurityExpressionVisitor extends ExpressionVisitorAdapter {
    @Override
    public void visit(JdbcParameter parameter) {
        super.visit(parameter);
    }
}