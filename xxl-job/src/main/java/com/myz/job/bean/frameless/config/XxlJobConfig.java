/**
 * Copyright 2020 Inc.
 **/
package com.myz.job.bean.frameless.config;

import com.myz.job.bean.frameless.jobhandler.FramelessJobHandler;
import com.xxl.job.core.executor.XxlJobExecutor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

/**
 * @author maoyz0621 on 20-4-25
 * @version v1.0
 */
@Slf4j
public class XxlJobConfig {
    private static String ADMIN_ADDRESSES = "http://127.0.0.1:8080/xxl-job-admin";
    private static String ACCESS_TOKEN = "";
    private static String APP_NAME = "";
    private static String ADDRESS = "";
    private static String IP = "";
    private static String PORT = "9999";
    private static String LOG_PATH = "/data/applogs/xxl-job/jobhandler";
    private static String LOG_RETENTION_DAYS = "30";

    private static XxlJobConfig instance = new XxlJobConfig();
    private XxlJobExecutor xxlJobExecutor = null;

    public static XxlJobConfig getInstance() {
        return instance;
    }

    public void initJobExecutor() {
        // 注册
        XxlJobExecutor.registJobHandler("framelessJobHandler", new FramelessJobHandler());

        // 加载属性文件
        Properties xxlJobProp = loadProperties("xxl-job-executor.properties");

        xxlJobExecutor = new XxlJobExecutor();
        xxlJobExecutor.setAdminAddresses(xxlJobProp.getProperty("xxl.job.admin.addresses", ADMIN_ADDRESSES));
        xxlJobExecutor.setAccessToken(xxlJobProp.getProperty("xxl.job.accessToken", ACCESS_TOKEN));
        xxlJobExecutor.setAppname(xxlJobProp.getProperty("xxl.job.executor.appname", APP_NAME));
        xxlJobExecutor.setAddress(xxlJobProp.getProperty("xxl.job.executor.address", ADDRESS));
        xxlJobExecutor.setIp(xxlJobProp.getProperty("xxl.job.executor.ip", IP));
        xxlJobExecutor.setPort(Integer.parseInt(xxlJobProp.getProperty("xxl.job.executor.port", PORT)));
        xxlJobExecutor.setLogPath(xxlJobProp.getProperty("xxl.job.executor.logpath", LOG_PATH));
        xxlJobExecutor.setLogRetentionDays(Integer.parseInt(xxlJobProp.getProperty("xxl.job.executor.logretentiondays", LOG_RETENTION_DAYS)));

        try {
            xxlJobExecutor.start();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    public void destoryXxlJobExecutor() {
        if (xxlJobExecutor != null) {
            xxlJobExecutor.destroy();
        }
    }

    private Properties loadProperties(String propertyFileName) {
        if ("".equals(propertyFileName)) {
            propertyFileName = "application.properties";
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(classLoader.getResourceAsStream(propertyFileName)), StandardCharsets.UTF_8);
        ) {
            Properties properties = new Properties();
            properties.load(reader);
            return properties;
        } catch (IOException e) {
            log.error("加载xxlJob属性配置文件{}　error", propertyFileName, e);
        }
        return null;
    }
}
