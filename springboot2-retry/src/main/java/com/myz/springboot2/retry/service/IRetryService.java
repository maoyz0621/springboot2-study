package com.myz.springboot2.retry.service;

/**
 * @author maoyz
 */
public interface IRetryService {

    int retry(int num);

    int jdkRetry(int num);

    int cglibRetry(int num);

}
