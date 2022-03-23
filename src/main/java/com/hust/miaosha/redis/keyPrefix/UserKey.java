package com.hust.miaosha.redis.keyPrefix;

/**
 * @program: miaosha1
 * @description: 用户模块的redisKey前缀
 * @author: XuJY
 * @create: 2022-02-27 00:04
 **/
public class UserKey extends BasePrefix{

    private UserKey(String keyPrefix) {
        super(keyPrefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");


}
