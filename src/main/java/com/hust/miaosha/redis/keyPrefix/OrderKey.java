package com.hust.miaosha.redis.keyPrefix;

/**
 * @program: miaosha1
 * @description:
 * @author: XuJY
 * @create: 2022-02-27 00:08
 **/
public class OrderKey extends BasePrefix {


    private OrderKey(String keyPrefix) {
        super(keyPrefix);
    }

    public static OrderKey getById = new OrderKey("id");
    public static OrderKey getByName = new OrderKey("name");

    public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");
    public static OrderKey getTuangouOrderByUidGid = new OrderKey("toug");

}
