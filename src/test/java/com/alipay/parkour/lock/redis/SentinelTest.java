package com.alipay.parkour.lock.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * {@see https://blog.csdn.net/shmilychan/article/details/73433804}
 *
 * @author recollects
 * @version V1.0
 * @date 2018年04月11日 下午11:09
 */
public class SentinelTest {

    public static final String MASTER_NAME = "mymaster";   // 定义哨兵的Master配置名称
    public static final int TIMEOUT = 2000;    // 连接超时时间
    public static final String REDIS_AUTH = "mldnjava";    // 认证密码
    public static final int MAX_TOTAL = 1000;  // 设置最大连接数
    public static final int MAX_IDLE = 200;    // 设置最小维持连接数
    public static final int MAX_WAIT_MILLIS = 1000;    // 设置最大等待时间

    public static void main(String[] args) {

        Set<String> sentinels = new HashSet<String>();

        sentinels.add("192.168.0.1:26379");    // 哨兵的地址
        sentinels.add("192.168.0.1:26380");    // 哨兵的地址
        sentinels.add("192.168.0.1:26381");    // 哨兵的地址

        JedisPoolConfig poolConfig = new JedisPoolConfig();

        poolConfig.setMaxTotal(MAX_TOTAL);  // 设置最大连接数
        poolConfig.setMaxIdle(MAX_IDLE);    // 设置空闲的连接数
        poolConfig.setMaxWaitMillis(MAX_WAIT_MILLIS);// 最大等待时间
        // 此时所有的连接应该通过哨兵机制取得，所以这个时候应该使用JedisSentinelPool对象
        JedisSentinelPool pool = new JedisSentinelPool(MASTER_NAME, sentinels,
                poolConfig);    // 建立一个哨兵的连接池
        Jedis jedis = pool.getResource();  // 通过连接池获取连接对象
        jedis.auth(REDIS_AUTH);
        System.out.println(jedis);
        jedis.set("hello", "yejiadong");
        jedis.close();
    }
}
