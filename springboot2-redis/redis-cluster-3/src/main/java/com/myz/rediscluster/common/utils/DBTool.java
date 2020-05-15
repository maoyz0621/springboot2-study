/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.rediscluster.common.utils;


import com.myz.rediscluster.common.utils.entity.PrimaryKeys;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author maoyz on 18-12-5
 * @version: v1.0
 */
public class DBTool {

    private static final Logger log = LoggerFactory.getLogger(DBTool.class);

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    private static final String apiSecretKey = "default";

    private static Pattern unique = Pattern.compile("^#.*#$");

    /**
     * 查询当前时间
     */
    public static String queryCurrentTime() {
        return FastDateFormat.getInstance("yyMMddHHmmss")
                .format(System.currentTimeMillis());
    }

    public static Object genePrimaryKey(String rule, Map<String, Object> tuple,
                                        PrimaryKeys pks, int index) throws ExecutionException {
        boolean isSingle = PrimaryKeys.isSingleKey(rule.split(":")[0]);
        String formatKey = PrimaryKeys.formatKey(rule.split(":")[0]);
        Object id;

        if (null == tuple) {
            tuple = new HashMap<>();
        }

        if (isSingle) {
            id = pks.getSingleKey(formatKey);
        } else {
            id = pks.getKey(formatKey, index);
        }

        if (id == null) {
            id = getTicket(rule, tuple);
            if (isSingle) {
                pks.addSingleKeys(formatKey, id);
            } else {
                pks.addKey(id, formatKey, index);
            }
            tuple.put("GEN_" + formatKey, id);
        }
        return id;
    }

    static String formatStub(String stub) {
        if (unique.matcher(stub).matches()) {
            return stub.substring(1, stub.length() - 1);
        }
        return stub;
    }

    public static String getTicket(String rule, Map<String, Object> tuple) {
        if (null == tuple) {
            tuple = new HashMap<>();
        }
        String seq = null;
        try {
            seq = new KeyGenerator(rule).getKeys(tuple).get(formatStub(rule.split(":")[0])).toString();
        } catch (Exception e) {
            log.error("获取主键错误：{}", e);
        }
        return seq;
    }


    /**
     * 通过时间戳获取Signature.
     *
     * @return
     */
    public static String getSignature() {

        String expectedSignature = null;
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String timestamp = ts.toString();
        log.debug("Timestamp timestamp = " + timestamp);
        try {
            SecretKeySpec keySpec = new SecretKeySpec(apiSecretKey.getBytes(), HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(keySpec);
            expectedSignature = new String(Base64.encodeBase64(mac.doFinal(timestamp.getBytes())));
        } catch (Exception e) {
            log.error("Exception message : {}", e.getMessage());
        }
        return expectedSignature;
    }

    /**
     * 防止sql注入.
     */
    public static boolean sql(String str) throws Exception {
        if (str.length() > 50) {
            if (log.isDebugEnabled()) {
                log.debug("column length more than 50, value is {}.\n column's length is {}", str, str.length());
            }
            return false;
        }
        String reg = "(?i).*('|and\\s|exec\\s|execute\\s|insert\\s|select\\s|delete\\s|update\\s|count\\s|drop\\s|" +
                "\"|\\$|\\||<|>|&|%|,|;|--|//|/|#|@|\\+|\\*|\\(|\\)|\\\"|\\\\|\\^|" +
                "char\\s|declare\\s|sitename\\s|net user\\s|xp_cmdshell\\s|like'|create\\s|" +
                "table\\s|from\\s|grant\\s|use\\s|group_concat\\s|column_name\\s|" +
                "information_schema\\.columns|table_schema\\s|union\\s|where\\s|by\\s|" +
                "chr\\s|mid\\s|master\\s|truncate\\s|or\\s+|like\\s|\\n|\\r).*";
        if (str.matches(reg)) {
            log.debug("sql does not matches" + str);
            return false;
        }
        return true;
    }

    /**
     * 获取UUID.
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}