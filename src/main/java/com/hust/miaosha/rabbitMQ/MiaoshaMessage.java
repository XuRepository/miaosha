package com.hust.miaosha.rabbitMQ;

import com.hust.miaosha.domain.MiaoshaUser;

import java.io.Serializable;

/**
 * @program: miaosha1
 * @description: 秒杀消息队列的消息
 * @author: XuJY
 * @create: 2022-03-04 17:22
 **/
public class MiaoshaMessage implements Serializable {
    private MiaoshaUser user;
    private long goodsId;
    public MiaoshaUser getUser() {
        return user;
    }
    public void setUser(MiaoshaUser user) {
        this.user = user;
    }
    public long getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}