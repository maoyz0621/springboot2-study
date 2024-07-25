/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.core;

import com.myz.log.record.core.context.LogRecordEvaluationContext;
import com.myz.log.record.core.expression.LogRecordExpressionEvaluator;
import org.springframework.context.expression.AnnotatedElementKey;

/**
 * SpEL 模板解析器
 *
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */
public class LogRecordValueParser {

    private LogRecordEvaluationContext logRecordEvaluationContext;

    private final LogRecordExpressionEvaluator expressionParser;

    public LogRecordValueParser(LogRecordEvaluationContext logRecordEvaluationContext,
                                LogRecordExpressionEvaluator expressionParser) {
        this.logRecordEvaluationContext = logRecordEvaluationContext;
        this.expressionParser = expressionParser;
    }

    public String parseExpression(String expression, AnnotatedElementKey methodKey) {
        return expressionParser.parseExpression(expression, methodKey, logRecordEvaluationContext);
    }

    public void setLogRecordEvaluationContext(LogRecordEvaluationContext logRecordEvaluationContext) {
        this.logRecordEvaluationContext = logRecordEvaluationContext;
    }

}