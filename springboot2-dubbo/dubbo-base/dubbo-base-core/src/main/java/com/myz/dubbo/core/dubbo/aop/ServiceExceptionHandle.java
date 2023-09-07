// /*
//  * Copyright (C) 2020, All rights Reserved, Designed By
//  * @author: maoyz
//  * @Copyright: 2020-11-13 15:47  Inc. All rights reserved.
//  */
// package com.myz.dubbo.core.dubbo.aop;
//
// import com.myz.dubbo.core.Result;
// import org.apache.commons.lang3.exception.ExceptionUtils;
// import org.aspectj.lang.ProceedingJoinPoint;
// import org.aspectj.lang.annotation.Around;
// import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Pointcut;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;
//
// import java.nio.ByteBuffer;
// import java.util.concurrent.TimeoutException;
//
// /**
//  * @author maoyz
//  */
// @Component
// @Aspect
// @Order(100)
// public class ServiceExceptionHandle {
//     private static Logger logger = LoggerFactory.getLogger(ServiceExceptionHandle.class);
//     @Autowired
//     private AlertServiceClient mAlertServiceClient;
//
//     public ServiceExceptionHandle() {
//     }
//
//     @Pointcut("execution(public com.oppo.mkt.utils.network.result.Result cn.oppo..*Service*.*(..))")
//     private void servicePointcut() {
//     }
//
//     @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
//     private void transactionalPointcut() {
//     }
//
//     @Around("servicePointcut()")
//     public Object doAround(ProceedingJoinPoint pjp) {
//         Object[] args = pjp.getArgs();
//
//         try {
//             return pjp.proceed();
//         } catch (ResultHandleException var5) {
//             return Result.fail(var5.getRetCode(), var5.getRetMsg());
//         } catch (Exception var6) {
//             Throwable cause = ExceptionUtils.getRootCause(var6);
//             this.processException(pjp, args, var6);
//             return cause instanceof TimeoutException ? Result.fail(ResultStatusEnum.TIMEOUT.getStatus(), var6.getMessage()) : Result.fail("服务调用失败, " + var6.getMessage());
//         } catch (Throwable var7) {
//             this.processException(pjp, args, var7);
//             return Result.fail("系统异常");
//         }
//     }
//
//     private void processException(ProceedingJoinPoint joinPoint, Object[] args, Throwable throwable) {
//         String inputParam = "";
//         if (args != null && args.length > 0) {
//             StringBuilder sb = new StringBuilder();
//             Object[] var6 = args;
//             int var7 = args.length;
//
//             for(int var8 = 0; var8 < var7; ++var8) {
//                 Object arg = var6[var8];
//                 sb.append(",");
//                 if (null == arg) {
//                     sb.append("null");
//                 } else {
//                     sb.append(arg);
//                 }
//             }
//
//             inputParam = sb.toString().substring(1);
//         }
//
//         String errorInfo = String.format("方法: %s\n 入参: %s\n 错误信息: %s", joinPoint.toLongString(), inputParam, Throwables.getStackTraceAsString(throwable));
//         logger.error(errorInfo);
//         this.mAlertServiceClient.SendEmail("Dubbo调用业务异常", errorInfo, (ByteBuffer)null, (String)null);
//         this.mAlertServiceClient.SendTTNotice(errorInfo);
//     }
// }