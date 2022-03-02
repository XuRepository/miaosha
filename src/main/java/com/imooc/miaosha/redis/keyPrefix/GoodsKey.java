package com.imooc.miaosha.redis.keyPrefix;

/**
 * @program: miaosha1
 * @description: 用户模块的redisKey前缀
 * @author: XuJY
 * @create: 2022-02-27 00:04
 **/
public class GoodsKey extends BasePrefix{

    private GoodsKey(int expire,String keyPrefix) {
        super(expire,keyPrefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60,"goodList");//页面缓存的 有效期为60s
    public static GoodsKey getGoodsDetail = new GoodsKey(60, "goodDetail");



}
