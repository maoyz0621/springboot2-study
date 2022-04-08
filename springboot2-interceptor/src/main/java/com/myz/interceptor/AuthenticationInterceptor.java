package com.myz.interceptor;

import com.myz.annotations.NeedLogin;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 登录拦截器
 *
 * @author maoyz
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    /*
     * 目标方法调用之前使用，可以进行权限，日志，事务处理等
     * 1 当返回false时终止
     * 2 返回true时继续运行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle()...");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        NeedLogin annotation = method.getAnnotation(NeedLogin.class);
        if (annotation == null) {
            return true;
        }

        // 执行认证
        String token = request.getHeader("token");  // 从 http 请求头中取出 token
        if (StringUtils.isEmpty(token)) {
            response.getWriter().print("无token，请重新登录");
            // throw new RuntimeException("无token，请重新登录");
            return false;
        }
        // 解析token -> userId
        Long userId = 1L;
        request.setAttribute("currentUser", userId);
        // todo 设置全局信息
        return true;
    }

    /*
     * 目标方法之后，视图渲染之前使用
     * 可以对请求域的属性和视图进行修改
     * */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle()...");
    }

    /*
     * 渲染视图之后使用，释放资源
     * */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion()...");
    }
}
