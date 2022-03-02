package com.imooc.miaosha.redis;

import com.alibaba.fastjson.JSON;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.redis.keyPrefix.KeyPrefix;
import com.imooc.miaosha.redis.keyPrefix.MiaoshaUserKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @program: miaosha1
 * @description:
 * @author: XuJY
 * @create: 2022-02-25 22:59
 **/
@Service
public class RedisService {


    @Autowired
    RedisConfig redisConfig;

    @Resource
    JedisPool jedisPool;

    /**
     * 获取当个对象
     * */
    public <T> T get(KeyPrefix prefix,String key, Class<T> clazz){

        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();

            //获取真正的key：例如 user:id *;
            String realKey = prefix.getPrefix() + key;

            String str = jedis.get(realKey);

            T res = stringToBean(str,clazz);

            return res;

        }finally {
            //如果jedis不为空，就关闭jedis。
            returnToPool(jedis);
        }

    }

    /**
     * 设置对象
     * */
    public <T> boolean set(KeyPrefix prefix , String key,T t){

        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();

            String str = beanToString(t);

            //获取真正的key，加上前缀

            if (str == null || str.length()==0){
                return false;
            }

            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            int seconds =  prefix.expireSeconds();

            if(seconds <= 0) {
                jedis.set(realKey, str);
            }else {
                jedis.setex(realKey, seconds, str);
            }
            return true;


        }finally {
            //如果jedis不为空，就关闭jedis。
            returnToPool(jedis);
        }

    }

    /**
     * 判断Key是否存在
     * @param prefix
     * @param key
     * @return
     */
    public boolean exits(KeyPrefix prefix,String key){
        Jedis jedis =null;
        try {
            jedis = jedisPool.getResource();

            String realKey = prefix.getPrefix()+key;
            return jedis.exists(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加值
     * */
    public <T> Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.incr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 减少值
     * */
    public <T> Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.decr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }


    private <T> String beanToString(T value) {

        if(value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return ""+value;
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }

    }

    private <T> T stringToBean(String str,Class<T> clazz) {

        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class) {
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(str);
        }else {

            //如果不是基本类型，就使用JSON转为java对象，对象格式为clazz
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }


    }


    private void returnToPool(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }

    /**
     * 删除
     * @param
     */
    public long delete(KeyPrefix prefix,String key){
        Jedis jedis =null;
        try {
            jedis = jedisPool.getResource();

            String realKey = prefix.getPrefix()+key;
            return jedis.del(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

}
