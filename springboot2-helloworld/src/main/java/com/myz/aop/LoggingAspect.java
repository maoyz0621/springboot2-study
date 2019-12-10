package com.myz.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @author maoyz on 18-3-11.
 */
@Component
@Aspect
public class LoggingAspect {
    /**
     * 日志
     */
    private final static Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.example.demo.MybatisController.*(..))")
    private void log() {
    }

    @Before("log()")
    public void before(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        logger.info("url:" + request.getRequestURI() + "\r\n" +
                "method:" + request.getMethod() + "\r\n" +
                "ip:" + request.getRemoteAddr() + "\r\n" +
                // 类名和类方法
                "className:" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "\r\n" +
                "args:" + args + "\r\n" + "\r\n"
        );
    }

    @AfterReturning(value = "log()", returning = "retVal")
    public void afterReturning(Object retVal) {
        logger.info("returning:" + retVal);
    }

    @AfterThrowing(value = "log()", throwing = "e")
    public void afterThrowing(Exception e) {
        logger.info("exception:" + e);
    }

}
