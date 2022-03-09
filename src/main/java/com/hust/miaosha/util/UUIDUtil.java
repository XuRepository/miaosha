package com.hust.miaosha.util;

import java.util.UUID;

/**
 * @program: miaosha1
 * @description: UUID 用户生成token标识分布式session
 * @author: XuJY
 * @create: 2022-02-27 21:08
 **/
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }


}
