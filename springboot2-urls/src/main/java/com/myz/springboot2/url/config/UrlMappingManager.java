/**
 * Copyright 2023 Inc.
 **/
package com.myz.springboot2.url.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author maoyz0621 on 2023/9/11
 * @version v1.0
 */
@Component
public class UrlMappingManager {

    private static final List<Map<String, String>> URL_LIST = new CopyOnWriteArrayList<>();

    private static final Map<String, String> URL_MAP = new ConcurrentHashMap<>(256);

    private Set<String> noLoginUrlSet = new HashSet<>();

    @Autowired
    private WebApplicationContext applicationContext;

    public void initUrlMappings() {
        // RequestMappingHandlerMapping
        RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();

        handlerMethods.forEach((k, v) -> {
            // 请求类型
            RequestMethodsRequestCondition methodsCondition = k.getMethodsCondition();
            Set<RequestMethod> methods = methodsCondition.getMethods();
            Map<String, String> urlMap = new HashMap<>(256);
            // 请求url
            PatternsRequestCondition patternsCondition = k.getPatternsCondition();
            if (!CollectionUtils.isEmpty(patternsCondition.getPatterns())) {
                patternsCondition.getPatterns().forEach(pattern -> {
                    if (!noLoginUrlSet.contains(pattern)) {
                        URL_MAP.put(pattern, pattern);
                        urlMap.put(pattern, pattern);
                    }
                });
            }
            URL_LIST.add(urlMap);
        });
    }

    public static List<Map<String, String>> getList() {
        return URL_LIST;
    }

    public static Map<String, String> getMap() {
        return URL_MAP;
    }
}