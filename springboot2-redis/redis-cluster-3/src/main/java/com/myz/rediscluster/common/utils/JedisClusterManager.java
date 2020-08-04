/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.rediscluster.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.util.JedisClusterCRC16;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author maoyz on 2018/12/03
 * @version: v1.0
 */
public class JedisClusterManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(JedisClusterManager.class);

    private static final int DEFAULT_MIN_IDLE = 10;
    private static final int DEFAULT_MAX_IDLE = 100;
    private static final int DEFAULT_MAX_TOTAL = 1000;
    private static final int DEFAULT_MAX_WAIT_MILLIS = 60000;
    private static final boolean DEFAULT_TEST_ON_BORROW = true;
    private static final int DEFAULT_TIME_OUT = 60000;
    private static final int DEFAULT_MAX_REDIRECTS = 5;
    private static final String REDIS_CONF_FILE = "sequence-generator.properties";
    private static final String MAX_IDLE = "server.conn.maxIdle";

    /**
     * redis集群所有节点和对应的JedisPool
     */
    private Map<String, JedisPool> jedisPoolMap;
    /**
     * redis集群所有的节点信息
     */
    private TreeMap<Long, String> slotMap;
    private Properties properties;
    private JedisCluster jedisCluster;
    private int minIdel;
    private int maxIdle;
    private int maxTotal;
    private int maxwaitmillis;
    private boolean testonborrow;
    private int timeout;
    private int maxredirects;

    private JedisClusterManager() {
        init();
    }

    private void init() {
        LOGGER.info("Init redis cluster server start ... ");
        properties = SeqGeneratorUtils.loadProperties(REDIS_CONF_FILE);
        try {
            minIdel = properties.containsKey("server.conn.minIdel") ?
                    Integer.parseInt(properties.getProperty("server.conn.minIdel")) :
                    DEFAULT_MIN_IDLE;
            maxIdle = properties.containsKey(MAX_IDLE) ?
                    Integer.parseInt(properties.getProperty(MAX_IDLE)) :
                    DEFAULT_MAX_IDLE;
            maxTotal = properties.containsKey(MAX_IDLE) ?
                    Integer.parseInt(properties.getProperty("server.conn.maxTotal")) :
                    DEFAULT_MAX_TOTAL;
            maxwaitmillis = properties.containsKey("server.conn.maxwaitmillis") ?
                    Integer.parseInt(properties.getProperty("server.conn.maxwaitmillis")) :
                    DEFAULT_MAX_WAIT_MILLIS;
            testonborrow = properties.containsKey("server.testonborrow") ?
                    Boolean.parseBoolean(properties.getProperty("server.testonborrow")) :
                    DEFAULT_TEST_ON_BORROW;
            timeout = properties.containsKey("server.conn.timeout") ?
                    Integer.parseInt(properties.getProperty("server.conn.timeout")) :
                    DEFAULT_TIME_OUT;
            maxredirects = properties.containsKey("server.maxredirects") ?
                    Integer.parseInt(properties.getProperty("server.maxredirects")) :
                    DEFAULT_MAX_REDIRECTS;

            GenericObjectPoolConfig jedisPoolConfig =
                    getGenericObjectPoolConfig(minIdel, maxIdle, maxTotal, maxwaitmillis, testonborrow);

            Set<HostAndPort> redisNodeSet = getHostAndPorts(properties);

            // 初始化JedisCluster
            jedisCluster = new JedisCluster(redisNodeSet, timeout, maxredirects, jedisPoolConfig);

            // 集群节点
            jedisPoolMap = jedisCluster.getClusterNodes();
            if (null != jedisPoolMap && !jedisPoolMap.isEmpty()) {
                String anyHostAndPort = jedisPoolMap.keySet().iterator().next();
                slotMap = getSlotHostMap(anyHostAndPort);
            }
            LOGGER.info("Init redis cluster server finished.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("An buildFailure occurred when initialize redis cluster server : {}", e.getMessage());
        }
    }

    /**
     * 集群配置点
     *
     * @param properties
     * @return
     */
    private static Set<HostAndPort> getHostAndPorts(Properties properties) {
        String redisNodesStr = properties.getProperty("server.redis.nodes");
        if (StringUtils.isBlank(redisNodesStr)) {
            throw new IllegalArgumentException("The configuration [server.redis.nodes] is not correct.");
        }

        String[] redisNodes = redisNodesStr.split(",");
        //构造结点
        Set<HostAndPort> redisNodeSet = new HashSet<>();
        for (String redisNode : redisNodes) {
            String[] redisInfoArr = redisNode.split(":");
            String host = redisInfoArr[0];
            int port = Integer.parseInt(redisInfoArr[1]);
            redisNodeSet.add(new HostAndPort(host, port));
        }
        return redisNodeSet;
    }

    /**
     * 初始化连接池
     */
    private static GenericObjectPoolConfig getGenericObjectPoolConfig(int minIdel, int maxIdle, int maxTotal, int maxwaitmillis, boolean testonborrow) {
        GenericObjectPoolConfig jedisPoolConfig = new GenericObjectPoolConfig();
        jedisPoolConfig.setMinIdle(minIdel);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxWaitMillis(maxwaitmillis);
        jedisPoolConfig.setTestOnBorrow(testonborrow);
        return jedisPoolConfig;
    }

    private static class JedisClusterManagerHolder {
        private static JedisClusterManager instance = new JedisClusterManager();
    }

    public static JedisClusterManager getInstance() {
        return JedisClusterManagerHolder.instance;
    }

    public String get(String key) {
        return jedisCluster.get(key);
    }

    private TreeMap<Long, String> getSlotHostMap(String anyHostAndPortStr) {
        TreeMap<Long, String> slotMapTmp = new TreeMap<>();
        String[] arr = anyHostAndPortStr.split(":");

        LOGGER.info("host={}, port={}", arr[0], arr[1]);

        HostAndPort anyHostAndPort = new HostAndPort(arr[0], Integer.parseInt(arr[1]));
        Jedis jedis = new Jedis(anyHostAndPort.getHost(), anyHostAndPort.getPort());

        List<Object> slotObj = jedis.clusterSlots();
        for (Object slots : slotObj) {
            ArrayList<Object> slotInfo = (ArrayList<Object>) slots;
            ArrayList<Object> master = (ArrayList<Object>) slotInfo.get(2);
            String hostAndPort = new String((byte[]) master.get(0)) + ":" + master.get(1);
            slotMapTmp.put((Long) slotInfo.get(0), hostAndPort);
            slotMapTmp.put((Long) slotInfo.get(1), hostAndPort);
        }
        return slotMapTmp;
    }

    /**
     * 获取JedisCluster
     */
    public JedisCluster getCluster() {
        return jedisCluster;
    }

    /**
     * 根据KEY获取该KEY对应卡槽的Jedis
     */
    public Jedis getJedis(String key) {
        String master = getMaster(key);
        if (null != jedisPoolMap && !jedisPoolMap.isEmpty()
                && null != master && !master.equals("")) {
            return jedisPoolMap.get(master).getResource();
        }
        return null;
    }

    public Jedis getMasterJedis(String master) {
        if (null != jedisPoolMap && !jedisPoolMap.isEmpty()
                && null != master && !master.equals("")) {
            return jedisPoolMap.get(master).getResource();
        }
        return null;
    }

    /**
     * 根据KEY获取对应存储主节点ip和port
     */
    public String getMaster(String key) {
        if (null != key && !key.equals("")) {
            try {
                if (null != slotMap && !slotMap.isEmpty()) {
                    return slotMap.floorEntry(Long.valueOf(JedisClusterCRC16.getSlot(key))).getValue();
                }
            } catch (Exception e) {
                LOGGER.error("Can not get the master for [key: {}, slot: {}]", key);
            }
        }
        return null;
    }

    public Set<String> getAllMaster() {
        Set<String> masterSet = new HashSet<>();
        if (null != slotMap && !slotMap.isEmpty()) {
            Collection<String> coll = slotMap.values();
            if (null != coll && !coll.isEmpty()) {
                for (String master : coll) {
                    masterSet.add(master);
                }
            }
        }
        return masterSet;
    }

    /**
     * 将Redis协议格式数据进行分组, KEY分节点存储
     * 不建议在超大数据时使用
     */
    public Map<String, LinkedBlockingQueue<String>> group(Map<String, LinkedBlockingQueue<String>> map,
                                                          String key, String protocol) throws InterruptedException {
        Map<String, LinkedBlockingQueue<String>> container = map;
        if (null == container) {
            container = new HashMap<>();
        }

        String master = getMaster(key);
        if (null == master) {
            return container;
        }

        LinkedBlockingQueue<String> queue = container.get(master);
        if (null == queue) {
            queue = new LinkedBlockingQueue<>();
        }
        queue.put(protocol);

        container.put(master, queue);

        return container;
    }


}
