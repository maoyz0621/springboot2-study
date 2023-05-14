执行顺序：

1、HandlerInterceptor.preHandle()

2、 业务方法

3、@ExceptionHandler

4、ResponseBodyAdvice.supports()

5、ResponseBodyAdvice.beforeBodyWrite()

6、HandlerInterceptor.postHandle()

7、HandlerInterceptor.afterCompletion()