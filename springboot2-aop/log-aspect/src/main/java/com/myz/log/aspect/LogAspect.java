/**
 * Copyright 2019 Inc.
 **/
package com.myz.log.aspect;

import com.alibaba.fastjson.JSON;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * 统一日志切面,打印入参
 *
 * @author maoyz0621 on 19-6-11
 * @version: v1.0
 */
@Aspect
@Component
@Order(1)
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private static final String COMMA = ",";

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void logPoint() {
    }

    /**
     * 使用环绕通知
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("logPoint()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        before(joinPoint);
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 放行
        Object object = joinPoint.proceed();
        stopWatch.stop();
        after(object, stopWatch.getTotalTimeMillis());
        return object;
    }

    private void before(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = HttpContextUtils.getServletRequest();
        // 获取IP
        String ip = IpUtils.getIpAddress(request);
        // 获取url
        String uri = request.getRequestURI();
        Enumeration<String> names = request.getHeaderNames();

        List<String> header = new ArrayList<>();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            header.add(name + " = " + request.getHeader(name));
        }

        String method = request.getMethod();

        if (logger.isInfoEnabled()) {
            String message = "请求相关信息：\n [请求ip地址] -> [{}],\n [请求url] -> [{}],\n【请求头信息】->【{}】,\n【请求方法】->【{} {}】,\n【请求参数】->【{}】";
            logger.info(message, ip, uri, StringUtils.join(header, COMMA), method, joinPoint.getSignature(), Arrays.toString(getArgs(joinPoint)));
        }
    }

    /**
     * 忽略HttpServletRequest HttpServletResponse
     */
    private Object[] getArgs(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        List<Object> argList = new ArrayList<>(args.length);
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse ||
                    arg instanceof BeanPropertyBindingResult || arg instanceof RedirectAttributes
                    || arg instanceof MultipartFile) {
                continue;
            }
            argList.add(arg);
        }

        return argList.toArray();
    }

    private void after(Object object, long totalTimeMillis) {
        if (logger.isInfoEnabled()) {
            String message = "执行情况：\n执行时间为：【{}毫秒】\n返回值为：【{}】";
            logger.info(message, totalTimeMillis, JSON.toJSONString(object));
        }
    }

}
