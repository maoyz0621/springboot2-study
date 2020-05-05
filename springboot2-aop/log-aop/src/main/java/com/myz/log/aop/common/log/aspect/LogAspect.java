package com.myz.log.aop.common.log.aspect;

import com.myz.log.aop.common.log.annotation.Log;
import com.myz.log.aop.common.log.annotation.LogEvent;
import com.myz.log.aop.common.log.annotation.LogEventType;
import com.myz.log.aop.common.log.annotation.LogModuleType;
import com.myz.log.aop.common.log.manager.model.LogModel;
import com.myz.log.aop.common.utils.HttpContextUtils;
import com.myz.log.aop.common.utils.IpUtils;
import com.myz.log.aop.common.log.manager.ILogManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * AOP 记录用户操作日志
 * <p>
 * 关于调用 JoinPoint 和 RequestContextHolder。
 * 通过JoinPoint可以获得通知的签名信息，如目标方法名、目标方法参数信息等。
 * 通过RequestContextHolder来获取请求信息，Session信息。
 *
 * @author maoyz0621 on 2018/12/27
 * @version: v1.0
 */
@Component
@Aspect
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private LogInfoGeneration logInfoGeneration;
    @Autowired
    private ILogManager logManager;

    /**
     * 织入点,使用自定义注解
     * todo @annotation和@within  @target的区别
     * 其中 @annotation是方法级别,ElementType.METHOD; @within是类级别ElementType.TYPE,要求RetentionPolicy.CLASS; @target是类级别ElementType.TYPE,要求RetentionPolicy.RUNTIME
     */
    @Pointcut("@annotation(com.myz.log.aop.common.log.annotation.Log))")
    public void logPoint() {
    }

    /**
     * 织入点，包下
     */
    @Pointcut("execution(* com.myz.logaop.module.web..*.*(..))")
    public void doLog() {
    }

    /**
     * 1. 检查拦截方法的类是否被@Log注解，如果是，则走日志逻辑，否则执行正常的逻辑
     * 2. 检查拦截方法是否被@LogEvent，如果是，则走日志逻辑，否则执行正常的逻辑
     * 3. 根据获取方法上获取@LogEvent 中值，生成日志的部分参数。其中定义在类上@LogEvent 的值做为默认值
     * 4. 调用logInfoGeneration的processingManagerLogMessage填充日志中其它的参数
     * 5. 执行正常的业务调用
     * 6. 如果执行成功，则logManager执行日志的处理（我们这里只记录执行成功的日志，也可以定义记录失败的日志）
     */
    @Around(value = "logPoint() && @annotation(logAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint, Log logAnnotation) throws Throwable {
        // 获取request
        HttpServletRequest request = HttpContextUtils.getServletRequest();
        String ip = IpUtils.getIpAddress(request);
        String uri = request.getRequestURI();

        logger.debug("{} : {}", ip, uri);

        long start = System.currentTimeMillis();
        Object result;

        Class<?> target = joinPoint.getTarget().getClass();
        // 获取Log注解
        Log annotation = target.getAnnotation(Log.class);
        // 1 没有Log注解或不开启注解，直接放行
        if (annotation == null || !annotation.logEnable()) {
            result = joinPoint.proceed();
            return result;
        }

        // 获取类上的LogEvent注解
        LogEvent logEventAnnotation = target.getAnnotation(LogEvent.class);
        Method method = getInvokedMethod(joinPoint);
        if (method == null) {
            return joinPoint.proceed();
        }

        // 获取方法上LogEvent注解
        LogEvent logEventMethodAnnotation = method.getAnnotation(LogEvent.class);
        if (logEventMethodAnnotation == null) {
            return joinPoint.proceed();
        }

        // 方法注解的值
        String event = logEventMethodAnnotation.event().getEvent();
        String desc = logEventMethodAnnotation.desc();
        String module = logEventMethodAnnotation.module().getModule();

        // 如果方法上的值为默认值，则使用全局的值进行替换
        if (logEventAnnotation != null) {
            event = LogEventType.DEFAULT.getEvent().equals(event) ?
                    logEventAnnotation.event().getEvent() : event;

            module = module.equals(LogModuleType.DEFAULT.getModule()) ?
                    logEventAnnotation.module().getModule() : module;
        }

        LogModel logModel = new LogModel();
        logModel.setDesc(desc);
        logModel.setOperateEvent(event);
        logModel.setOperateModel(module);

        LocalDateTime dateTime = LocalDateTime.now();
        String format = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        logModel.setCreateDate(new Date());

        // 处理日志信息
        logInfoGeneration.processingManagerLogMessage(joinPoint, logModel, method);

        // 执行方法
        result = joinPoint.proceed();

        // 登陆
        if (event.equals(LogEventType.LOGIN.getEvent())) {
            if (result != null) {
                logManager.handleLog(logModel);
            }
        } else {
            logManager.handleLog(logModel);
        }

        long end = System.currentTimeMillis();
        logger.debug("共消耗 {} ms", (end - start));

        return result;
    }

    /**
     * 获取请求方法
     */
    private Method getInvokedMethod(JoinPoint joinPoint) {
        List<Class> argList = new ArrayList<>();

        // 获取方法参数
        for (Object arg : joinPoint.getArgs()) {
            argList.add(arg.getClass());
        }

        Class[] argClass = (Class[]) argList.toArray(new Class[0]);

        String methodName = joinPoint.getSignature().getName();

        logger.debug("方法名：{}, 参数：{}", methodName, argList.toString());
        Method method = null;
        try {
            // 获取方法
            method = joinPoint.getTarget().getClass().getMethod(methodName, argClass);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return method;
    }

}