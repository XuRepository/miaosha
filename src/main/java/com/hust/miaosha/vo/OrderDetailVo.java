package com.hust.miaosha.vo;

import com.google.common.collect.Ordering;
import com.hust.miaosha.domain.OrderInfo;

/**
 * @program: miaosha1
 * @description:
 * @author: XuJY
 * @create: 2022-03-03 19:52
 **/
public class OrderDetailVo  {

    private GoodsVo goods;
    private OrderInfo order;
    public GoodsVo getGoods() {
        return goods;
    }
    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }
    public OrderInfo getOrder() {
        return order;
    }
    public void setOrder(OrderInfo order) {
        this.order = order;
    }

}
