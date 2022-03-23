package com.hust.miaosha.redis;

import com.alibaba.fastjson.JSON;
import com.hust.miaosha.redis.keyPrefix.KeyPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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


    public  <T> String beanToString(T value) {

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

    public  <T> T stringToBean(String str,Class<T> clazz) {

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

    /**
     * 删除redis中的全部该前缀的key
     * @param prefix
     * @return
     */
    public boolean delete(KeyPrefix prefix) {
        if(prefix == null) {
            return false;
        }
        List<String> keys = scanKeys(prefix.getPrefix());
        if(keys==null || keys.size() <= 0) {
            return true;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(keys.toArray(new String[0]));
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 扫描KV，并存入list
     * @param key
     * @return
     */
    public List<String> scanKeys(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> keys = new ArrayList<String>();
            String cursor = "0";
            ScanParams sp = new ScanParams();
            sp.match("*"+key+"*");
            sp.count(100);
            do{
                ScanResult<String> ret = jedis.scan(cursor, sp);
                List<String> result = ret.getResult();
                if(result!=null && result.size() > 0){
                    keys.addAll(result);
                }
                //再处理cursor
                cursor = ret.getStringCursor();
            }while(!cursor.equals("0"));
            return keys;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}
