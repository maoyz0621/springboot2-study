/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.aop.common.log.aspect;

import com.alibaba.fastjson.JSONObject;
import com.myz.log.aop.common.log.annotation.LogKey;
import com.myz.log.aop.common.log.manager.model.LogModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 日志消息生成
 *
 * @author maoyz0621 on 18-12-29
 * @version: v1.0
 */
@Component
public class LogInfoGeneration {
    private final Logger logger = LoggerFactory.getLogger(LogInfoGeneration.class);

    /**
     *
     */
    public void processingManagerLogMessage(ProceedingJoinPoint joinPoint, LogModel logModel, Method method) {
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            JSONObject jsonObject = new JSONObject();

            // 获取方法上参数所有的注解
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();

            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];

                // 如果参数被 LogKey 注解了，则直接返回内容
                if (checkArgAnnotationWithIsLogKey(arg, i, parameterAnnotations, jsonObject)) {
                    continue;
                }

                // 获取成员变量
                Field[] fields = arg.getClass().getDeclaredFields();
                for (Field field : fields) {
                    // 获取成员变量注解
                    Annotation[] fieldAnnotations = field.getAnnotations();
                    for (Annotation fieldAnnotation : fieldAnnotations) {
                        String fieldName = field.getName();
                        // 判定是否是LogKey注解 且isLog()是true
                        if ((fieldAnnotation instanceof LogKey) && ((LogKey) fieldAnnotation).isLog()) {
                            // 如果注解的有定义keyName，则覆盖对象成员变量名称
                            String keyName = ((LogKey) fieldAnnotation).keyName();
                            if (!StringUtils.isEmpty(keyName)) {
                                fieldName = keyName;
                            }

                            try {
                                // 将private变为public
                                field.setAccessible(true);
                                Object fieldVal = field.get(arg);
                                jsonObject.put(fieldName, fieldVal);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
                logModel.setOperateContent(jsonObject.toJSONString());
            }
            logger.debug(logModel.toString());
        }
    }


    /**
     * 如果方法参数被Logkey注解，则将获取整个类的信息
     */
    private boolean checkArgAnnotationWithIsLogKey(Object arg, int index, Annotation[][] parameterAnnotations, JSONObject jsonObject) {
        for (Annotation parameterAnnotation : parameterAnnotations[index]) {
            if (parameterAnnotation instanceof LogKey) {
                LogKey logKey = (LogKey) parameterAnnotation;
                if (logKey.isLog()) {
                    String keyName = logKey.keyName();
                    jsonObject.put(keyName, arg.toString());
                }
                break;
            }
        }
        return false;
    }
}
