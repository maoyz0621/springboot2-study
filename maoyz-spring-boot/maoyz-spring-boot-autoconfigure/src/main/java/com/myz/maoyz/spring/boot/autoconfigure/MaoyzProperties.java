package com.myz.maoyz.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author maoyz
 */
@ConfigurationProperties(prefix = MaoyzProperties.MYZ_PREFIX)
public class MaoyzProperties {

    public static final String MYZ_PREFIX = "myz";

    private String val;

    private boolean cache;
    /**
     * list属性
     */
    private List<String> sizes;

    /**
     * set属性
     */
    private Set<String> sets;

    /**
     * map属性
     */
    private Map<String, String> param;

    /**
     * class类
     */
    private Class<?> clazz;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public Set<String> getSets() {
        return sets;
    }

    public void setSets(Set<String> sets) {
        this.sets = sets;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
