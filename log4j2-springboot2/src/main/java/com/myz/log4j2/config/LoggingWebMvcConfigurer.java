/**
 * Copyright 2024 Inc.
 **/
package com.myz.log4j2.config;

import com.myz.log4j2.utils.MdcUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author maoyz0621 on 2024/7/8
 * @version v1.0
 */
public class LoggingWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                     @NonNull Object handler) throws Exception {
                // 请求头设置trace_id
                // nginx		proxy_set_header X-Request-Id $request_id;
                String traceId = MdcUtils.setTraceId(request.getHeader(MdcUtils.HTTP_HEADER_REQUEST_ID));
                return true;
            }

            @Override
            public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                        @NonNull Object handler, Exception ex) {
                MdcUtils.clear();
            }
        });
    }
}