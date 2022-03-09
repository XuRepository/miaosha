package com.hust.miaosha.redis.keyPrefix;

/**
 * @program: miaosha1
 * @description:
 * @author: XuJY
 * @create: 2022-02-27 21:28
 **/
public class MiaoshaUserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;       // token 过期时间, 2天

    private MiaoshaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }


    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE, "tk");
    public static MiaoshaUserKey getById = new MiaoshaUserKey(0,"id");


}
