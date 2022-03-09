package com.hust.miaosha.redis.keyPrefix;

/**
 * @program: miaosha1
 * @description: 用于客户都安轮询秒杀是否成功的过程中，判断商品是否卖光的情况
 * @author: XuJY
 * @create: 2022-02-27 00:04
 **/
public class MiaoshaKey extends BasePrefix{

    private MiaoshaKey(String keyPrefix) {
        super(keyPrefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey("goodOver");//页面缓存的 有效期为60s


}
