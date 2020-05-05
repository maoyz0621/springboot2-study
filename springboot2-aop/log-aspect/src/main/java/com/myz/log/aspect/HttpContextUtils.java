/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.aspect;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author maoyz0621 on 18-12-28
 * @version v1.0
 */
public class HttpContextUtils {

    private HttpContextUtils() {
    }

    /**
     * 获取HttpServletRequest
     */
    public static HttpServletRequest getServletRequest() {

        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
    }

    /**
     * 获取HttpServletRequest
     */
    public static HttpServletRequest getServletRequestWithReference() {
        return (HttpServletRequest) Objects.requireNonNull(
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())
                        .resolveReference(RequestAttributes.REFERENCE_REQUEST));
    }

    /**
     * 获取HttpSession
     */
    public static HttpSession getHttpSession() {
        return (HttpSession) Objects.requireNonNull(
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).
                        resolveReference(RequestAttributes.REFERENCE_SESSION));
    }
}
