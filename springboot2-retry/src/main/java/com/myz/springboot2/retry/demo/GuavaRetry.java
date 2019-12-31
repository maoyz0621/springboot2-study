package com.myz.springboot2.retry.demo;

import com.github.rholder.retry.*;
import com.google.common.base.Predicates;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author maoyz
 */
@Slf4j
public class GuavaRetry {

    public static void main(String[] args) {
        Callable<User> callable = () -> {
            System.out.println(LocalDateTime.now());
            return new User("mao", 12);
        };

        // main0(callable);

        main1(callable);
    }

    private static void main0(Callable<User> callable) {
        Retryer<User> retryer = RetryerBuilder.<User>newBuilder()
                .retryIfExceptionOfType(IOException.class)
                .retryIfRuntimeException()
                // 断言, 根据结果进行是否重试
                .retryIfResult(Predicates.not((user) -> user != null && "mao1".equals(user.getUsername())))
                .withRetryListener(new RetryListener() {

                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        log.error("第【{}】次调用失败", attempt.getAttemptNumber());
                    }
                })
                // 停止重试策略
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .build();

        try {
            User call = retryer.call(callable);
            System.out.println(call);
        } catch (ExecutionException | RetryException e) {
            e.printStackTrace();
        }
    }

    private static void main1(Callable<User> callable) {
        Retryer<User> retryer = RetryerBuilder.<User>newBuilder()
                .retryIfExceptionOfType(IOException.class)
                .retryIfRuntimeException()
                // 断言, 根据结果进行是否重试
                .retryIfResult(Predicates.not((user) -> user != null && "mao0".equals(user.getUsername())))
                .withRetryListener(new RetryListener() {

                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        log.error("第【{}】次调用失败", attempt.getAttemptNumber());
                    }
                })
                // 停止重试策略
                .withStopStrategy(StopStrategies.stopAfterAttempt(5))
                // 等待时长策略, 提供一个初始值和步长，等待时间随重试次数增加而增加
                .withWaitStrategy(WaitStrategies.incrementingWait(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS))
                .build();

        try {
            User call = retryer.call(callable);
            System.out.println(call);
        } catch (ExecutionException | RetryException e) {
            e.printStackTrace();
        }
    }
}

class User {
    private String username;
    private Integer age;

    public User() {
    }

    public User(String username, Integer age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("username='").append(username).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}
