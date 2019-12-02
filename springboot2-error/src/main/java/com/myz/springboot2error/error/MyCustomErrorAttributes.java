/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2error.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * springboot提供error
 *
 * @author maoyz0621 on 19-12-2
 * @version: v1.0
 */
@Component
@Slf4j
public class MyCustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        log.info("MyCustomErrorAttributes getErrorAttributes()");

        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        errorAttributes.put("local", webRequest.getLocale().toString());
        errorAttributes.put("status", "100000");
        return errorAttributes;
    }
}
