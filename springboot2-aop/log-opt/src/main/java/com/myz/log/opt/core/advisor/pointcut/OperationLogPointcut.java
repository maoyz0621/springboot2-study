package com.myz.log.opt.core.advisor.pointcut;

import com.myz.log.opt.core.annotation.OperationLog;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;


/**
 * @author maoyz0621
 */
public class OperationLogPointcut extends StaticMethodMatcherPointcut implements Serializable {
    private static final long serialVersionUID = 8555462312067444226L;

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        Annotation operationLogAnnotation = AnnotationUtils.findAnnotation(method, OperationLog.class);
        return Objects.nonNull(operationLogAnnotation);
    }
}
