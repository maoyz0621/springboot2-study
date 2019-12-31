# Spring-retry

## 1. 结构

+ **RetryCallback**: 封装你需要重试的业务逻辑（上文中的doSth）
+ **RecoverCallback**：封装在多次重试都失败后你需要执行的业务逻辑(上文中的doSthWhenStillFail)
+ **RetryContext**: 重试语境下的上下文，可用于在多次Retry或者Retry和Recover之间传递参数或状态（在多次doSth或者doSth与doSthWhenStillFail之间传递参数）
+ **RetryOperations** : 定义了“重试”的基本框架（模板），要求传入RetryCallback，可选传入RecoveryCallback；
+ **RetryTemplate**: RetryOperations的具体实现，组合了RetryListener[]，BackOffPolicy，RetryPolicy
+ **RetryListener**：典型的“监听者”，在重试的不同阶段通知“监听者”（例如doSth，wait等阶段时通知）
+ **RetryPolicy** : 重试的策略或条件，可以简单的进行多次重试，可以是指定超时时间进行重试（上文中的someCondition）
+ **BackOffPolicy**: 重试的回退策略，在业务逻辑执行发生异常时。如果需要重试，我们可能需要等一段时间(可能服务器过于繁忙，如果一直不间隔重试可能拖垮服务器)


## 2. 重试策略

+ **NeverRetryPolicy**：只允许调用RetryCallback一次，不允许重试
+ **AlwaysRetryPolicy**：允许无限重试，直到成功，此方式逻辑不当会导致死循环
+ **SimpleRetryPolicy**：固定次数重试策略，默认重试最大次数为3次，RetryTemplate默认使用的策略
+ **TimeoutRetryPolicy**：超时时间重试策略，默认超时时间为1秒，在指定的超时时间内允许重试
+ **ExceptionClassifierRetryPolicy**：设置不同异常的重试策略，类似组合重试策略，区别在于这里只区分不同异常的重试
+ **CircuitBreakerRetryPolicy**：有熔断功能的重试策略，需设置3个参数openTimeout、resetTimeout和delegate
+ **CompositeRetryPolicy**：组合重试策略，有两种组合方式，乐观组合重试策略是指只要有一个策略允许重试即可以，


## 3. 重试回退策略

重试回退策略，指的是每次重试是立即重试还是等待一段时间后重试。

默认情况下是立即重试，如果需要配置等待一段时间后重试则需要指定回退策略BackoffRetryPolicy。

+ **NoBackOffPolicy**：无退避算法策略，每次重试时立即重试
+ **FixedBackOffPolicy**：固定时间的退避策略，需设置参数sleeper和backOffPeriod，sleeper指定等待策略，默认是Thread.sleep，即线程休眠，backOffPeriod指定休眠时间，默认1秒
+ **UniformRandomBackOffPolicy**：随机时间退避策略，需设置sleeper、minBackOffPeriod和maxBackOffPeriod，该策略在[minBackOffPeriod,maxBackOffPeriod之间取一个随机休眠时间，minBackOffPeriod默认500毫秒，maxBackOffPeriod默认1500毫秒
+ **ExponentialBackOffPolicy**：指数退避策略，需设置参数sleeper、initialInterval、maxInterval和multiplier，initialInterval指定初始休眠时间，默认100毫秒，maxInterval指定最大休眠时间，默认30秒，multiplier指定乘数，即下一次休眠时间为当前休眠时间*multiplier
+ **ExponentialRandomBackOffPolicy**：随机指数退避策略，引入随机乘数可以实现随机乘数回退


```
    RetryTemplate retryTemplate = new RetryTemplate();
    
    // 重试策略
    SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
    retryPolicy.setMaxAttempts(4);
    
    // 监听器
    RetryListener listener = new RetryListener() {
    
        /**
         * 返回true, 放行
         * @param context
         * @param callback
         * @param <T>
         * @param <E>
         * @return
         */
        @Override
        public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
            System.out.println("RetryListener open");
            return true;
        }
    
        @Override
        public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
            System.out.println("RetryListener close");
        }
    
        @Override
        public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
            System.out.println("RetryListener onError");
        }
    };
    
    retryTemplate.setRetryPolicy(retryPolicy);
    retryTemplate.setListeners(new RetryListener[]{listener});
    
    try {
        Object execute = retryTemplate.execute(context -> {
            System.out.println("RetryCallback " + context.getRetryCount());
            throw new RuntimeException();
        }, context -> {
            // 回退策略
            System.out.println("RecoveryCallback " + context.getLastThrowable());
            return "RecoveryCallback";
        });
        
    } catch (Throwable throwable) {
        
    }

```


## Guava-retry


接口 |	描述 | 备注
---|---|---
Attempt	| 一次执行任务	
AttemptTimeLimiter | 单次任务执行时间限制|	如果单次任务执行超时，则终止执行当前任务
BlockStrategies	|任务阻塞策略|	通俗的讲就是当前任务执行完，下次任务还没开始这段时间做什么），默认策略为：BlockStrategies.THREAD_SLEEP_STRATEGY
RetryException |	重试异常	
RetryListener |	自定义重试监听器|	可以用于异步记录错误日志
StopStrategy |	停止重试策略	
WaitStrategy |	等待时长策略|	（控制时间间隔），返回结果为下次执行时长
