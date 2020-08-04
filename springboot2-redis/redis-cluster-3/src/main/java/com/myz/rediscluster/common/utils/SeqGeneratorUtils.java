/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.rediscluster.common.utils;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author maoyz on 18-12-4
 * @version: v1.0
 */
public class SeqGeneratorUtils {

    private static Logger logger = LoggerFactory.getLogger(SeqGeneratorUtils.class);
    /**
     * 6位日期 + 6位时间格式
     */
    private static final FastDateFormat seqDateFormat = FastDateFormat.getInstance("yyMMddHHmmss");

    /**
     * 加载配置文件.properties
     */
    public static Properties loadProperties(String propertyFileName) {
        Properties props;
        final File propFile = new File(propertyFileName);
        try (final InputStream is = propFile.isFile() ?
                new FileInputStream(propFile) :
                (SeqGeneratorUtils.class.getResourceAsStream(propertyFileName) != null ?
                        SeqGeneratorUtils.class.getResourceAsStream(propertyFileName) :
                        SeqGeneratorUtils.class.getClassLoader().getResourceAsStream(propertyFileName))) {
            if (null != is) {
                props = new Properties();
                props.load(is);
                return props;
            } else {
                throw new RuntimeException("Cannot find property file: " + propertyFileName);
            }
        } catch (IOException e) {
            logger.error("buildFailure = {}", e);
        }
        return null;
    }

    private static String generateSeqPrefix(Long unixTimestamp) {
        Long timestamp;
        if (null == unixTimestamp) {
            timestamp = System.currentTimeMillis();
        } else {
            timestamp = unixTimestamp * 1000;
        }
        return seqDateFormat.format(timestamp);
    }

    /**
     * 获取lua脚本文件
     */
    public static String getScriptContent(String filename) {
        if (StringUtils.isBlank(filename)) {
            return null;
        }
        InputStream is = null;
        BufferedInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;
        StringBuffer script = new StringBuffer();
        try {
            is = SeqGenerator.class.getClassLoader().getResourceAsStream(filename);
            fis = new BufferedInputStream(is);
            isr = new InputStreamReader(fis, "utf-8");
            // 用5M的缓冲读取文本文件
            reader = new BufferedReader(isr, 5 * 1024 * 1024);
            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line + "\r\n");
            }
            return script.toString();
        } catch (UnsupportedEncodingException e) {
            logger.error("{}", e);
        } catch (IOException e) {
            logger.error("{}", e);
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
                if (null != fis) {
                    fis.close();
                }
                if (null != isr) {
                    fis.close();
                }
                if (null != reader) {
                    fis.close();
                }
            } catch (IOException e) {
                logger.error("{}", e);
            }
        }
        return null;
    }

    public static String buildSeq(Long unixTimestamp, Long seqSuffix, int seqLength) {
        StringBuilder seqBuilder = new StringBuilder();
        String seqPrefix = generateSeqPrefix(unixTimestamp);
        return seqBuilder.append(seqPrefix).append(String.format("%0" + (seqLength - seqPrefix.length()) + "d", seqSuffix)).toString();
    }

    /**
     * 获取执行次数限制的重试策略
     */
    public static Retryer<String> getExecutionTimesLimitRetryer() {
        Retryer<String> retryer = RetryerBuilder.<String>newBuilder()
                //如果返回结果为 null 则重试
                .retryIfResult(Predicates.isNull())
                //如果抛出 Exception 则重试
                .retryIfExceptionOfType(Exception.class)
                //最多尝试 3 次
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                //固定等待时长为 300 ms(返回下次执行的时间)
                .withWaitStrategy(WaitStrategies.fixedWait(300, TimeUnit.MILLISECONDS))
                .build();
        return retryer;
    }

}
