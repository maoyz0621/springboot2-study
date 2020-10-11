/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.i18n.config;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author maoyz0621 on 2020/9/30
 * @version v1.0
 */
public class MyLocaleResolver implements LocaleResolver {

    public static final String REQUEST_LANGUAGE = "Accept-Language";
    public static final String REQUEST_USER_AGENT = "User-Agent";
    public static final String REQUEST_USER_AGENT_BROWSER = "Mozilla";
    public static final String REQUEST_LANGUAGE_SPLIT_BROWSER = "-";
    public static final String REQUEST_LANGUAGE_SPLIT_OTHER = "_";

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        // accept-language: zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6
        String languageHeader = request.getHeader(REQUEST_LANGUAGE);
        Locale locale = Locale.getDefault();
        if (!StringUtils.isEmpty(languageHeader)) {
            String[] split;
            if (request.getHeader(REQUEST_USER_AGENT).startsWith(REQUEST_USER_AGENT_BROWSER)) {
                // 浏览器  zh-CN
                split = languageHeader.split(REQUEST_LANGUAGE_SPLIT_BROWSER);
            } else {
                // 程序请求 zh_CN
                split = languageHeader.split(REQUEST_LANGUAGE_SPLIT_OTHER);
            }
            locale = new Locale(split[0], split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}