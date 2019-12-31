package com.myz.springboot2.retry.demo;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * @author maoyz
 */
public class SpringRetry {

    public static void main(String[] args) {
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
            System.out.println("result: " + execute);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
