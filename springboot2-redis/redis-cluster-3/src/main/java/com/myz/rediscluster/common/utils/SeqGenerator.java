/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.rediscluster.common.utils;

import com.github.rholder.retry.Retryer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 加载 lua 脚本工具类.
 * 序列生成：通过redis集群实现，redis存入的key为key=SEQ_<name>_<m>
 * * <name>为传入参数，<m>通过每次取模m随机生成。
 * * sequence默认18位=12位yyMMddHHmmss+6位value（前补0），每次加m,返回的ID=value<br/>
 * * 也可传入序列长度，最小位数不能低于12位。
 *
 * @author maoyz on 18-12-4
 * @version: v1.0
 */
public class SeqGenerator {

    private static final Logger logger = LoggerFactory.getLogger(SeqGenerator.class);
    private static final FastDateFormat yyMMDateFormat = FastDateFormat.getInstance("yyMM");
    /**
     * 生成序列的配置文件
     */
    private static final String SEQUENCE_GENERATOR_CONF = "sequence-generator.properties";
    /**
     * 初始化生成序列的lua脚本
     */
    private static final String DEFAULT_SCRIPT_CONF = "script-init-node.lua";
    /**
     * 序列key的前缀
     */
    private static final String SEQ_KEY_PREFIX = "SEQ_";
    /**
     * 默认时差
     */
    private static final String DEFAULT_LAG = "8";
    private int incrAmount;
    private int defaulLength;
    private String scriptContent;
    private String luaSha;
    /**
     * 重试机制
     */
    private Retryer<String> retryer;

    private SeqGenerator() {
        init();
    }

    private void init() {
        try {
            Properties properties = SeqGeneratorUtils.loadProperties(SEQUENCE_GENERATOR_CONF);
            incrAmount = Integer.parseInt(
                    properties.getProperty("sequence.incr_amount"));
            logger.debug("incrAmount :{}", incrAmount);
            defaulLength = Integer.parseInt(
                    properties.getProperty("sequence.default_length"));
            scriptContent = SeqGeneratorUtils.getScriptContent(
                    properties.getProperty("sequence.scripts"));
            if (StringUtils.isBlank(scriptContent)) {
                scriptContent = SeqGeneratorUtils.getScriptContent(DEFAULT_SCRIPT_CONF);
            }
            retryer = SeqGeneratorUtils.getExecutionTimesLimitRetryer();
            luaSha = JedisClusterManager.getInstance().getCluster().scriptLoad(scriptContent, "");
        } catch (Exception e) {
            logger.error("实例化序列失败: {}", e);
        }
    }

    private static class SeqGeneratorHolder {
        private static SeqGenerator instance = new SeqGenerator();
    }

    public static SeqGenerator getInstance() {
        return SeqGeneratorHolder.instance;
    }

    /**
     * 默认生成18位的序列
     *
     * @param seqName 字段名称、表名
     * @return
     */
    public Long next(String seqName) {
        return Long.parseLong(next(seqName, defaulLength));
    }

    /**
     * 自定义生成序列的位数
     */
    public String next(String seqName, int seqLength) {
        int mod = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE) % incrAmount;
        String seqKey = SEQ_KEY_PREFIX + seqName + "_" + mod;
        String sequence;
        logger.debug("seqKey={}", seqKey);
        try {
            sequence = retryer.call(buildRetryTask(seqKey, seqLength, mod));
        } catch (Exception e) {
            throw new RuntimeException("生成序列失败，请检查redis集群状况！");
        }
        logger.debug("ret sequence={}", sequence);
        return sequence;
    }

    /**
     * 生成序列的主方法
     *
     * @param seqKey    redis存入的key
     * @param seqLength 序列长度
     * @param initValue 序列初始值后缀
     * @return
     */
    private String innerNext(String seqKey, int seqLength, int initValue) {
        String yyMMOfSeq = yyMMDateFormat.format(System.currentTimeMillis());
        String sequence = String.valueOf(
                JedisClusterManager.getInstance().getCluster().incrBy(seqKey, incrAmount));
        // 如果是第一次则进入lua重置
        if (sequence.length() < seqLength) {
            try {
                return JedisClusterManager.getInstance().getCluster().evalsha(
                        luaSha,
                        1,
                        seqKey,
                        String.valueOf(initValue),
                        String.valueOf(incrAmount),
                        String.valueOf(seqLength),
                        yyMMOfSeq,
                        DEFAULT_LAG
                ).toString();
            } catch (Exception e) {
                logger.error("redis node has no script!");
                return JedisClusterManager.getInstance().getCluster().eval(
                        scriptContent,
                        1,
                        seqKey,
                        String.valueOf(initValue),
                        String.valueOf(incrAmount),
                        String.valueOf(seqLength),
                        yyMMOfSeq,
                        DEFAULT_LAG
                ).toString();
            }
        }
        // 下个月则替换年份+月份
        if (!sequence.startsWith(yyMMOfSeq)) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(yyMMOfSeq).append(sequence.substring(4));
            return buffer.toString();
        }
        logger.debug("sequence={}", sequence);
        return sequence;
    }

    /**
     * 建立重试任务
     *
     * @param seqKey
     * @param seqLength
     * @param initValue
     * @return
     */
    private Callable<String> buildRetryTask(String seqKey, int seqLength, int initValue) {
        return () -> innerNext(seqKey, seqLength, initValue);
    }
}
