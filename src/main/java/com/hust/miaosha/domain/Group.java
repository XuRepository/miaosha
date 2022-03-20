package com.hust.miaosha.domain;

/**
 * @program: miaosha1
 * @description: 团购秒杀
 * @author: XuJY
 * @create: 2022-03-09 14:06
 **/
public class Group {

    private long groupId;
    private int headCount;
    private int targetCount;//折扣=headCount/targetCount
//    private long goodsId;


    public Group() {
    }

    public Group(long groupId, int headCount, int targetCount, long goodsId) {
        this.groupId = groupId;
        this.headCount = headCount;
        this.targetCount = targetCount;
//        this.goodsId = goodsId;
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

    public int getTargetCount() {
        return targetCount;
    }

    public void setTargetCount(int targetCount) {
        this.targetCount = targetCount;
    }

    public double getDiscount(){
        return 1.0-1.0*headCount/targetCount;
    }

//    public long getGoodsId() {
//        return goodsId;
//    }
//
//    public void setGoodsId(long goodsId) {
//        this.goodsId = goodsId;
//    }
}
