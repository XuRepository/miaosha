package com.imooc.miaosha.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @program: miaosha1
 * @description:
 * @author: XuJY
 * @create: 2022-02-26 00:21
 **/
@Service
public class RedisPoolFactory {

    @Autowired
    RedisConfig redisConfig;


    //之所以这个bean新建一个类，避免放在ReidsService中形成循环引用。
    @Bean
    public JedisPool jedisPoolFactory(){

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        jedisPoolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait()*1000);//毫秒和秒转换

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisConfig.getHost(), redisConfig.getPort(),
                redisConfig.getTimeout() * 1000, redisConfig.getPassword(), 0);

        return jedisPool;
    }
}
