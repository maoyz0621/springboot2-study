/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2.i18n.config;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author maoyz0621 on 19-6-30
 * @version v1.0
 */
@Component
public class I18nMessageUtil implements MessageSourceAware {

    private static MessageSourceAccessor accessor;

    /**
     * 获取i18n文件中对应的国际化信息
     *
     * @param code   i18n文件中code
     * @param locale 地区信息
     * @param args   参数
     * @return 国际化信息
     */
    public static String getMessage(String code, Locale locale, Object... args) {
        if (locale == null) {
            return accessor.getMessage(code, args);
        }
        return accessor.getMessage(code, args, locale);
    }

    /**
     * 获取i18n文件中对应的国际化信息,如果不传local信息，则从当前request获取，如果还是没有，则使用默认local
     *
     * @param code i18n文件中code
     * @param args 参数
     * @return 国际化信息
     */
    public static String getMessage(String code, Object... args) {
        return accessor.getMessage(code, args);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        I18nMessageUtil.accessor = new MessageSourceAccessor(messageSource);
    }

}