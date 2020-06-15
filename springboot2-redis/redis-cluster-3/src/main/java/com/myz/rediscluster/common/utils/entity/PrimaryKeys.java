
/**
 * Copyright 2017 asiainfo Inc.
 **/
package com.myz.rediscluster.common.utils.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 主键
 */
public class PrimaryKeys implements Serializable {

    private static final long serialVersionUID = -691756748690097366L;

    private Map<String, Map<Integer, Object>> keys;

    private Map<String, Object> singleKeys;

    private static final Pattern PATTERN = Pattern.compile("^#.*#$");

    public PrimaryKeys() {
        this.keys = new LinkedHashMap<>();
        this.singleKeys = new LinkedHashMap<>();
    }

    public void addKey(Object id, String key, Integer index) {

        if (!this.keys.containsKey(key)) {
            this.keys.put(key, new LinkedHashMap<>());
        }

        if (!this.keys.get(key).containsKey(index)) {
            this.keys.get(key).put(index, id);
        }

    }

    public Object getKey(String key, Integer index) {

        if (!this.keys.containsKey(key)) {
            return null;
        }

        Map<Integer, Object> keyMap = this.keys.get(key);

        if (keyMap.containsKey(index)) {
            return keyMap.get(index);
        }

        return null;
    }

    public void addSingleKeys(String key, Object id) {
        this.singleKeys.put(key, id);
    }

    public Object getSingleKey(String key) {
        return this.singleKeys.get(key);
    }

    public static boolean isSingleKey(String key) {
        return PATTERN.matcher(key).matches();
    }

    public static String formatKey(String key) {
        if (isSingleKey(key)) {
            return key.substring(1, key.length() - 1);
        }
        return key;
    }

    @Override
    public String toString() {
        return "PrimaryKeys{" +
                "keys=" + keys +
                ", singleKeys=" + singleKeys +
                '}';
    }
}
