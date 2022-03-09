package com.hust.miaosha.redis.keyPrefix;

/**
 * @program: miaosha1
 * @description: 抽象Prefix类
 * @author: XuJY
 * @create: 2022-02-26 23:59
 **/
public abstract class BasePrefix implements KeyPrefix {

    private int expireSeconds;//0为默认，永不超时

    private String KeyPrefix;

    public BasePrefix(int expireSeconds, String keyPrefix) {
        this.expireSeconds = expireSeconds;
        KeyPrefix = keyPrefix;
    }

    //不指定有效时间，默认为0
    public BasePrefix(String keyPrefix) {
        this(0, keyPrefix);
    }


    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();

        return className+":"+KeyPrefix;
        //返回的是 模块：该模块内部的方法的前缀，
        // 例如： user:id  user:name  order:id

    }
}

