/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.rediscluster.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

/**
 * 主键生成工具类.
 *
 * @author maoyz on 18-12-4
 * @version: v1.0
 */
public class KeyGenerator {

    private Pattern unique = Pattern.compile("^#.*#$");

    private Map<String, GeneratedKeys> ruleMap = new LinkedHashMap<>();

    public KeyGenerator(String rules) {
        // 用逗号分隔的主键生产策略
        String[] rulesArr = rules.split(",");
        for (String rule : rulesArr) {
            // 冒号分隔每个主键生产策略
            pushMapper(rule);
        }
    }

    private void pushMapper(String rule) {
        String[] ruleArr = rule.split(":");
        String stub = ruleArr[0];
        String genTag = null;
        String mode = null;
        String ruleDetail = null;
        // 冒号分隔后数组个数
        switch (ruleArr.length) {
            case 2: {
                ruleDetail = ruleArr[1];
                break;
            }
            case 3: {
                if (ruleArr[1].startsWith("?")) {
                    genTag = ruleArr[1];
                } else {
                    mode = ruleArr[1];
                }
                ruleDetail = ruleArr[2];
                break;
            }
            case 4: {
                if (ruleArr[1].startsWith("?")) {
                    genTag = ruleArr[1];
                    mode = ruleArr[2];
                    ruleDetail = ruleArr[3];
                }
                break;
            }
        }
        String stubKey = unique.matcher(stub).matches() ?
                stub.substring(1, stub.length() - 1) : stub;
        ruleMap.put(stubKey, new GeneratedKeys(stub, genTag, mode, ruleDetail));
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getKeys(Map tuple) throws Exception {
        Map<String, Object> keysMap = new LinkedHashMap<>();

        for (String stub : ruleMap.keySet()) {
            GeneratedKeys generatedKeys = ruleMap.get(stub);
            Object key = generatedKeys.getKey();
//      if (Objects.isNull(key)) {
//        key = generatedKeys.generate(tuple);
//      }
//      if (null != key){
//        keysMap.put(stub, key);
//      }
            // 插入多条数据
            key = generatedKeys.generate(tuple);
            tuple.put(stub, key);
            generatedKeys.riseGenerateCount();
        }

        return keysMap;
    }

    public void resetCounter() {
        for (String stub : ruleMap.keySet()) {
            ruleMap.get(stub).resetCounter();
        }
    }

    private class GeneratedKeys {
        private String stub;
        private String genTag;
        private String mode;
        private String ruleDetail;
        private int generateCount = 0;
        private int resetCount = 0;

        private Map<String, Object> keysMap = new HashMap<>();

        /**
         * @param stub       字段名
         * @param genTag
         * @param mode       取模预算
         * @param ruleDetail 主键位数
         */
        GeneratedKeys(String stub, String genTag, String mode, String ruleDetail) {
            this.stub = stub;
            this.genTag = genTag;
            this.mode = mode;
            this.ruleDetail = ruleDetail;
        }

        Object generate(Map tuple) throws Exception {
            String formatStub = formatStub();
            Object key;

            if (genTag != null && !tuple.containsKey(genTag.substring(1))) {
                return null;
            }

            if (mode != null) {
                if (mode.startsWith("%")) {
                    key = generateModId(tuple, mode, ruleDetail);
                    keysMap.put(formatStub, key);
                } else {
                    key = null;
                }
            } else {
                if ("NOW".equals(ruleDetail)) {
                    key = DBTool.queryCurrentTime();
                    keysMap.put(formatStub, key);
                } else if (StringUtils.isNumeric(ruleDetail)) {
                    key = generateNumericId(stub, ruleDetail);
                    keysMap.put(formatStub, key);
                } else {
                    key = null;
                }
            }
            return key;
        }

        void resetCounter() {
            this.generateCount = 0;
            this.resetCount++;
        }

        private void riseGenerateCount() {
            this.generateCount++;
        }

        Object getKey() throws ExecutionException {
            String formatStub = formatStub();
            return keysMap.get(formatStub);
        }

        String formatStub() {
            if (unique.matcher(stub).matches()) {
                return stub.substring(1, stub.length() - 1);
            }
            if (mode != null && mode.startsWith("%")) {
                return stub + "-" + resetCount + "-" + generateCount;
            }
            if ("NOW".equals(ruleDetail)) {
                return stub;
            }
            if (StringUtils.isNumeric(ruleDetail)) {
                return stub + "-" + generateCount;
            }
            return stub + "-" + resetCount + "-" + generateCount;
        }

        /**
         * 主键生成主方法
         *
         * @param stub       主键名
         * @param ruleDetail 主键位数
         */
        Object generateNumericId(String stub, String ruleDetail) throws Exception {
            if ("18".equals(ruleDetail)) {
                return SeqGenerator.getInstance().next(stub);
            }
            //ChainService service = ToolFactory.getInstance().getChainService();
            //Context paramContext = new ContextBase();
            //paramContext.put("stub", stub);
            //paramContext.put("ruleDetail", ruleDetail);
            //Context result = service.serviceEntry("tcs.common:getTicketFlow", paramContext);
            //if (!CollectionUtils.isEmpty(result) && result.containsKey("seqId")) {
            //    return result.get("seqId");
            //}
            throw new RuntimeException("生成序列错误!");
        }

        Object generateModId(Map tuple, String mode, String ruleDetail) {
            String key = mode.substring(1);
            Object value = "";
            if (tuple.containsKey(key)) {
                value = tuple.get(key);
            } else if (tuple.containsKey("GEN_" + key)) {
                value = tuple.get("GEN_" + key);
            } else if (tuple.containsKey("V" + key)) {
                value = tuple.get("V" + key);
            }
            int pow = Integer.parseInt(ruleDetail);
            return Math.floorMod(Long.parseLong(value.toString()), (long) Math.pow(10, pow));
        }
    }
}
