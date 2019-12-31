package com.myz.springboot2.retry.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author maoyz
 */
@Service
@Slf4j
public class RetryServiceImpl implements IRetryService {

    private final int totalNum = 100000;

    /**
     * 使用重试注解 @Retryable
     * value：抛出指定异常才会重试, 例如连接超时
     * include：和value一样，默认为空，当exclude也为空时，默认所以异常
     * exclude：指定不处理的异常
     * maxAttempts：最大重试次数，默认3次
     * backoff：重试等待策略，默认使用@Backoff，@Backoff的value默认为1000L，我们设置为2000L；multiplier（指定延迟倍数）默认为0，表示固定暂停1秒后进行重试，如果把multiplier设置为1.5，则第一次重试为2秒，第二次为3秒，第三次为4.5秒。
     * <p>
     * 用于@Retryable重试失败后处理方法，@Recover注解，此方法里的异常一定要是@Retryable方法里抛出的异常，否则不会调用这个方法
     *
     * @param num
     * @return
     */
    @Retryable(value = ArithmeticException.class, maxAttempts = 4, backoff = @Backoff(delay = 2000L, multiplier = 1.5))
    @Override
    public int retry(int num) {
        return doWork(num);
    }

    @Override
    public int jdkRetry(int num) {
        return doWork(num);
    }

    @Override
    public int cglibRetry(int num) {
        return doWork(num);
    }

    private int doWork(int num) {
        log.info("========================================> {}", LocalDateTime.now());

        try {
            if (num == 1) {
                int i = 1 / 0;
            }
        } catch (Exception e) {
            throw new ArithmeticException(e.getMessage());
        }

        if (num <= 0) {
            throw new IllegalArgumentException("数量不对");
        }


        log.info("<========================================== {}", LocalDateTime.now());
        return totalNum - num;
    }

}
