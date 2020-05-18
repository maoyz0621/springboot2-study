package com.myz.redis1;

import com.myz.redis1.entity.User;
import com.vip.vjtools.vjkit.mapper.JsonMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RedisTester {

    Jedis jedis;

    @Before
    public void before() {
        jedis = RedisUtils.getJedis();
    }

    @After
    public void close() {
        RedisUtils.close(jedis);
    }

    @Test
    public void testConnect() {
        //　建立连接
        System.out.println(jedis.ping());
        jedis.set("token", "哈哈");
    }

    @Test
    public void testMap() {
        Map<String, String> param = new HashMap<>(4);
        Map<String, String> map1 = new HashMap<>(2);
        map1.putIfAbsent("aa", "11");
        map1.putIfAbsent("bb", "22");
        // json
        param.putIfAbsent("d", JsonMapper.INSTANCE.toJson(map1));
        param.putIfAbsent("a", "a1");
        param.putIfAbsent("b", "b1");
        param.putIfAbsent("c", "b1");
        jedis.hmset("allParams", param);
        System.out.println(jedis.hgetAll("allParams"));
        System.out.println(jedis.hget("allParams", "d"));
        // JSON -> Map
        System.out.println(JsonMapper.INSTANCE.fromJson(jedis.hget("allParams", "d"), Map.class).get("aa"));
        System.out.println(JsonMapper.INSTANCE.fromJson(jedis.hget("allParams", "d"), Map.class).get("a"));
    }

    @Test
    public void testBean() {
        Map<String, String> param = new HashMap<>(4);
        User user = new User("m1", 12);
        param.putIfAbsent("userA", JsonMapper.INSTANCE.toJson(user));
        jedis.hmset("users", param);
        System.out.println(jedis.hgetAll("users"));
    }

    @Test
    public void testKey() {
        jedis = RedisUtils.getJedis();
        this.jedis.set("k1", "mm1");
        this.jedis.set("k2", "mm2");
        this.jedis.set("k3", "mm3");

        Set<String> sets = jedis.keys("*");
        //　迭代set
        System.out.println(sets.size());
        for (Object set : sets) {
            System.out.println(set.toString());
        }

        //　迭代set
        Iterator<String> iterator = sets.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            System.out.println(str);
        }
        // jedis.flushDB();
    }

    /**
     * 测试事务
     * <p>
     * 事务操作返回 Response<String>
     */
    @Test
    public void testTransaction() throws IOException {
        jedis = RedisUtils.getJedis();
        jedis.set("k4", "k4_1");
        // 监控某key
        String watch = jedis.watch("k4");
        System.out.println("watch：" + watch);

        // k4的值发生了变化，执行的事务失效
        jedis.set("k4", "k4_2");

        jedis.set("k4", "k4_3");

        // 开始事务 Cannot use Jedis when in Multi. Please use Transation or reset jedis state.
        Transaction transaction = jedis.multi();

        // 执行操作,此时对k4操作无效
        transaction.set("k4", "mm4");
        transaction.set("k5", "mm5");
        transaction.sadd("k6", "a1", "a2", "a3");
        // 事务操作无法返回值，必须等事务执行
        System.out.println(transaction.spop("k6"));

        // todo 此时执行jedis，会报错 Cannot use Jedis when in Multi. Please use Transation or reset jedis state.
        // System.out.println(jedis.spop("k6"));

        // 执行事务
        transaction.exec();

        // todo 事务之后，才可以操作jedis,此时返回k4_3
        System.out.println(jedis.get("k4"));
        System.out.println(jedis.spop("k6"));

        transaction.close();
        RedisUtils.close(jedis);

        // if ("".equals(jedis.watch(k4))){
        //   // 回滚事务
        //   transaction.discard();
        // }
    }

    /**
     * 加锁
     */

    /**
     * 主从复制
     */
}
