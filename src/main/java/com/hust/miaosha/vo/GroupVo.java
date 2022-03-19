package com.hust.miaosha.vo;

/**
 * @program: miaosha1
 * @description: GroupVo
 * @author: XuJY
 * @create: 2022-03-19 13:57
 **/
public class GroupVo {

    private long groupId;
    private int headCount;
    private int targetCount;//折扣=headCount/targetCount
    private double discount;

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

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }
}
