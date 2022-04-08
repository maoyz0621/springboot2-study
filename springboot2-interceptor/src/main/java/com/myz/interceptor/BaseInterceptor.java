package com.myz.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 编写拦截器：
 * 1 实现HandlerInterceptor接口
 * 2 Override
 *
 * @author maoyz
 */
public class BaseInterceptor implements HandlerInterceptor {
    /*
     * 目标方法调用之前使用，可以进行权限，日志，事务处理等
     * 1 当返回false时终止
     * 2 返回true时继续运行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        System.out.println("BaseInterceptor　preHandle()...");
        request.setCharacterEncoding("utf-8");
        return true;
    }

    /*
     * 目标方法之后，视图渲染之前使用
     * 可以对请求域的属性和视图进行修改
     * */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("BaseInterceptor　postHandle()...");
    }

    /*
     * 渲染视图之后使用，释放资源
     * */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        System.out.println("BaseInterceptor　afterCompletion()...");
    }
}
