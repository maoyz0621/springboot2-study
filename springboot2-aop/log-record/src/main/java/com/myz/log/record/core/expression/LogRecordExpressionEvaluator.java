/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.core.expression;

import com.myz.log.record.core.CustomSpelExpressionParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */
public class LogRecordExpressionEvaluator extends CachedExpressionEvaluator {

    private static final Logger log = LoggerFactory.getLogger(LogRecordExpressionEvaluator.class);

    private final static Map<ExpressionKey, Expression> EXPRESSION_CACHE = new ConcurrentHashMap<>(64);

    private static final SpelExpressionParser EXPRESSION_PARSER = new CustomSpelExpressionParser();

    public LogRecordExpressionEvaluator() {
        super(EXPRESSION_PARSER);
    }

    public String parseExpression(String conditionExpression,
                                  AnnotatedElementKey methodKey,
                                  EvaluationContext evalContext) {
        String value = "";
        try{
            value = getExpression(EXPRESSION_CACHE, methodKey, conditionExpression)
                    .getValue(evalContext, String.class);
        }catch (Exception e){
            log.error("SpEl解析错误! 错误原因: {}", e.getMessage());
        }
        return value;
    }
}