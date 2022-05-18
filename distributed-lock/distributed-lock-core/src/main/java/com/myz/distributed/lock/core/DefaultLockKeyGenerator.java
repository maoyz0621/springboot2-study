/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.core;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author maoyz0621 on 2022/5/14
 * @version v1.0
 */
public class DefaultLockKeyGenerator implements LockKeyGenerator {

    private final static ParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    private final static ExpressionParser PARSER = new SpelExpressionParser();

    @Override
    public String generateKey(MethodInvocation invocation, String[] definitionKeys) {
        return generateKey(invocation.getMethod(), invocation.getArguments(), definitionKeys);
    }

    @Override
    public String generateKey(Method method, Object[] arguments, String[] definitionKeys) {
        if (definitionKeys.length == 0) {
            return "";
        }
        EvaluationContext evaluationContext = new MethodBasedEvaluationContext(null, method, arguments, NAME_DISCOVERER);
        List<String> definitionKeyList = new ArrayList<>(definitionKeys.length);
        for (String definitionKey : definitionKeys) {
            if (StringUtils.isEmpty(definitionKey)) {
                continue;
            }
            String key = PARSER.parseExpression(definitionKey).getValue(evaluationContext, String.class);
            definitionKeyList.add(key);
        }

        return StringUtils.collectionToDelimitedString(definitionKeyList, ".", "", "");
    }
}