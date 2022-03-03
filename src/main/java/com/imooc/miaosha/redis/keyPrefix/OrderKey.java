package com.imooc.miaosha.redis.keyPrefix;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.core.annotation.Order;

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

}
