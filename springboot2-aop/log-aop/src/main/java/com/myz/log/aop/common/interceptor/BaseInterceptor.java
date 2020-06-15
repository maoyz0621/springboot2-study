/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.aop.common.interceptor;


import com.myz.log.aop.common.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 拦截器
 *
 * @author maoyz0621 on 2018/12/28
 * @version: v1.0
 */
@Slf4j
public class BaseInterceptor implements HandlerInterceptor {

    /**
     * 记录request的参数值
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String remoteUser = request.getRemoteUser();
        String ip = IpUtils.getIpAddress(request);
        String uri = request.getRequestURI();

        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            StringBuilder sb = new StringBuilder();
            sb.append("{domain, name, value, version =");
            for (Cookie cookie : cookies) {
                String domain = cookie.getDomain();
                String name = cookie.getName();
                String value = cookie.getValue();
                int version = cookie.getVersion();
                sb.append(domain).append(":").append(name).append(":").append(value).append(":").append(version).append("; ");
            }
            sb.append("}");
            log.debug(sb.toString());
        }

        String method = request.getMethod();
        String sessionId = request.getSession().getId();
        log.debug("remoteUser = {}, ip = {}, uri = {}, method = {}, sessionId = {}", remoteUser, ip, uri, method, sessionId);

        Map<String, String[]> parameterMap = request.getParameterMap();
        // JDK8的迭代方式
        parameterMap.forEach((key, val) -> {
            log.debug("{} = {}", key, val);
        });

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
