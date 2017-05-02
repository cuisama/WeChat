package com.iss;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Redis {

    private static final String HOST = "192.168.86.85";
    private static final int PORT = 6379;
    private static final String PASSWORD = "123456";

    public static final String SET = "tokens";
    public static final String KEY = "token";
    /**
     * HashKey key1 value1,key2 value2 用户组名 密钥名称
     */
    public static final String USER_GROUP_HASH ="";


    private static JedisPool pool = null;

    /**
     * 从连接池获取Jedis
     * 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
     * config
     * setMaxTotal 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
     * setMaxIdle 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
     * setMaxWaitMillis 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
     * setTestOnBorrow 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
     * @return
     */
    public static Jedis getInstance() {
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(512);
            config.setMaxIdle(256);
            config.setMaxWaitMillis(1000L * 100L);
            config.setTestOnBorrow(true);
            pool = new JedisPool(config, HOST, PORT,10000,PASSWORD);
        }
        return pool.getResource();
    }

}
