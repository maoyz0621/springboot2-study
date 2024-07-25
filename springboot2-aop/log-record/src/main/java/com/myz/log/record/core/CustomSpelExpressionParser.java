/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.core;

import org.springframework.expression.Expression;
import org.springframework.expression.ParseException;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */
public class CustomSpelExpressionParser extends SpelExpressionParser {

    /**
     * EL前缀
     */
    public static final String prefix = "{";

    /**
     * EL后缀
     */
    public static final String suffix = "}";

    private TemplateParserContext templateParserContext;

    public CustomSpelExpressionParser() {
        templateParserContext = new TemplateParserContext(prefix, suffix);
    }

    @Override
    public Expression parseExpression(String expressionString) throws ParseException {
        return parseExpression(expressionString, templateParserContext);
    }

}