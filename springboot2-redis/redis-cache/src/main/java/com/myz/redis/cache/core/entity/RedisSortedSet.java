/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-11-17 19:55  Inc. All rights reserved.
 */
package com.myz.redis.cache.core.entity;

import com.myz.redis.cache.core.AbstractRedis;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;

/**
 * @author maoyz
 */
public class RedisSortedSet<V> extends AbstractRedis {

    private final BoundZSetOperations<String, V> operations;

    public RedisSortedSet(String key, RedisTemplate redisTemplate) {
        super(key, redisTemplate);
        this.operations = this.redisTemplate.boundZSetOps(this.key);
    }

    public Boolean add(V item, double score) {
        return item != null ? this.operations.add(item, score) : false;
    }

    public Long batchAdd(Map<V, Double> items) {
        if (items != null && items.size() > 0) {
            Set<ZSetOperations.TypedTuple<V>> typedTupleHashSet = new HashSet();
            Iterator var3 = items.entrySet().iterator();

            while (var3.hasNext()) {
                final Map.Entry<V, Double> item = (Map.Entry) var3.next();
                typedTupleHashSet.add(new ZSetOperations.TypedTuple<V>() {
                    public V getValue() {
                        return item.getKey();
                    }

                    public Double getScore() {
                        return (Double) item.getValue();
                    }

                    public int compareTo(ZSetOperations.TypedTuple<V> o) {
                        return this.getScore().compareTo(o.getScore());
                    }
                });
            }

            return this.operations.add(typedTupleHashSet);
        } else {
            return 0L;
        }
    }

    public Set<V> range(long start, long end, boolean descending) {
        return descending ? this.operations.reverseRange(start, end) : this.operations.range(start, end);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public Map<V, Double> rangeWithScores(long start, long end, boolean descending) {
        Set items;
        if (descending) {
            items = this.operations.reverseRangeWithScores(start, end);
        } else {
            items = this.operations.rangeWithScores(start, end);
        }

        if (items == null) {
            return null;
        } else {
            Map<V, Double> map = new TreeMap();
            Iterator var8 = items.iterator();

            while (var8.hasNext()) {
                ZSetOperations.TypedTuple<V> item = (ZSetOperations.TypedTuple) var8.next();
                map.put(item.getValue(), item.getScore());
            }

            return map;
        }
    }

    public Map<V, Double> sortRangeWithScores(long start, long end, boolean descending) {
        Set items;
        if (descending) {
            items = this.operations.reverseRangeWithScores(start, end);
        } else {
            items = this.operations.rangeWithScores(start, end);
        }

        if (items == null) {
            return null;
        } else {
            Map<V, Double> map = new LinkedHashMap();
            Iterator var8 = items.iterator();

            while (var8.hasNext()) {
                ZSetOperations.TypedTuple<V> item = (ZSetOperations.TypedTuple) var8.next();
                map.put(item.getValue(), item.getScore());
            }

            return map;
        }
    }

    public Set<V> rangeByScore(double min, double max, boolean descending) {
        return descending ? this.operations.reverseRangeByScore(min, max) : this.operations.rangeByScore(min, max);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public Map<V, Double> rangeByScoreWithScores(double min, double max, boolean descending) {
        Set items;
        if (descending) {
            items = this.operations.reverseRangeByScoreWithScores(min, max);
        } else {
            items = this.operations.rangeByScoreWithScores(min, max);
        }

        if (items == null) {
            return null;
        } else {
            Map<V, Double> map = new TreeMap();
            Iterator var8 = items.iterator();

            while (var8.hasNext()) {
                ZSetOperations.TypedTuple<V> item = (ZSetOperations.TypedTuple) var8.next();
                map.put(item.getValue(), item.getScore());
            }

            return map;
        }
    }

    public Map<V, Double> sortRangeByScoreWithScores(double min, double max, boolean descending) {
        Set items;
        if (descending) {
            items = this.operations.reverseRangeByScoreWithScores(min, max);
        } else {
            items = this.operations.rangeByScoreWithScores(min, max);
        }

        if (items == null) {
            return null;
        } else {
            Map<V, Double> map = new LinkedHashMap();
            Iterator var8 = items.iterator();

            while (var8.hasNext()) {
                ZSetOperations.TypedTuple<V> item = (ZSetOperations.TypedTuple) var8.next();
                map.put(item.getValue(), item.getScore());
            }

            return map;
        }
    }

    public Long size() {
        return this.operations.size();
    }

    public Long rank(V item, boolean descending) {
        if (item == null) {
            return 0L;
        } else {
            return descending ? this.operations.reverseRank(item) : this.operations.rank(item);
        }
    }

    public Double increment(V item, double score) {
        return item == null ? 0.0D : this.operations.incrementScore(item, score);
    }

    public Double score(V item) {
        return item == null ? 0.0D : this.operations.score(item);
    }

    public Long count(double min, double max) {
        return this.operations.count(min, max);
    }

    public boolean remove(V item) {
        if (item == null) {
            return false;
        } else {
            return this.operations.remove(new Object[]{item}) > 0L;
        }
    }

    public void removeByRange(long start, long end) {
        this.operations.removeRange(start, end);
    }

    public void removeByScore(double min, double max) {
        this.operations.removeRangeByScore(min, max);
    }

    public void clear() {
        this.operations.removeRange(0L, -1L);
    }

    public boolean contains(V item) {
        return this.operations.rank(item) != null;
    }
}
