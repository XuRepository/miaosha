package com.imooc.miaosha.redis.keyPrefix;

/**
 * @program: miaosha1
 * @description: key前缀
 * @author: XuJY
 * @create: 2022-02-26 23:57
 **/
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();

}
