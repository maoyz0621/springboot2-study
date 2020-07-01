/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2validation.common.annotation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 真正执行校验的类，对输入的手机执行校验
 *
 * @author maoyz0621 on 19-1-11
 * @version v1.0
 */
public class PhoneValidationValidator implements ConstraintValidator<PhoneValidation, String> {

    private static final Logger logger = LoggerFactory.getLogger(PhoneValidationValidator.class);

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$"
    );


    @Override
    public void initialize(PhoneValidation constraintAnnotation) {
        logger.debug("start PhoneValidationValidator initialize()" );
    }

    /**
     *
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.debug("start PhoneValidationValidator isValid(), value = {}", value );
        logger.debug("context = {}", context);
        if (StringUtils.isEmpty(value)){
            return true;
        }

        Matcher matcher = PHONE_PATTERN.matcher(value);
        return matcher.matches();
    }
}
