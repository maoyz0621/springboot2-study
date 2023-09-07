/**
 * Copyright 2023 Inc.
 **/
package com.myz.mybatis.sql.encrypt.config.visitor;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitorAdapter;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.mapping.BoundSql;

import java.util.List;

/**
 * @author maoyz0621 on 2023/6/29
 * @version v1.0
 */
public class SecurityItemsListVisitor extends ItemsListVisitorAdapter {

    private BoundSql boundSql;

    public SecurityItemsListVisitor(BoundSql boundSql) {
        this.boundSql = boundSql;
    }

    @Override
    public void visit(ExpressionList expressionList) {
        String newColumnVal = null;
        List<Expression> expressions = expressionList.getExpressions();
        for (Expression expression : expressions) {
            if (expression instanceof StringValue) {
                String value = ((StringValue) expression).getValue();
            } else if (expression instanceof JdbcParameter) {
                int index = ((JdbcParameter) expression).getIndex() - 1;
                String property = (boundSql.getParameterMappings().get(index)).getProperty();
                try {
                    String val0 = BeanUtils.getProperty(boundSql.getParameterObject(), property);
                    newColumnVal = val0 + "123456";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        expressionList.addExpressions(new Expression[]{new StringValue(newColumnVal)});
    }


    private class EncryptJdbcParameter extends JdbcParameter {

        private BoundSql boundSql;

        public BoundSql getBoundSql() {
            return boundSql;
        }

        public void setBoundSql(BoundSql boundSql) {
            this.boundSql = boundSql;
        }
    }
}