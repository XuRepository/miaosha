package com.imooc.miaosha.rabbitMQ;

import com.imooc.miaosha.domain.MiaoshaUser;

/**
 * @program: miaosha1
 * @description: 秒杀消息队列的消息
 * @author: XuJY
 * @create: 2022-03-04 17:22
 **/
public class MiaoshaMessage {
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