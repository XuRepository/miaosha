package com.hust.miaosha.domain;

/**
 * @program: miaosha1
 * @description: 团购秒杀
 * @author: XuJY
 * @create: 2022-03-09 14:06
 **/
public class Group {
    private int discount;
    private long groupId;
    private int headCount;
    private int targetHeadCount;//折扣=headCount/targetHeadCount
    private long goodsId;

    public Group(int discount, long groupId, int headCount, int targetHeadCount, long goodsId) {
        this.discount = discount;
        this.groupId = groupId;
        this.headCount = headCount;
        this.targetHeadCount = targetHeadCount;
        this.goodsId = goodsId;
    }

    public int getDiscount() {
        return headCount/targetHeadCount;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public int getHeadCount() {
        return headCount;
    }

    public void setHeadCount(int headCount) {
        this.headCount = headCount;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
