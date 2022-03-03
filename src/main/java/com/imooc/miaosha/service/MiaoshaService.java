package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: miaosha1
 * @description:
 * @author: XuJY
 * @create: 2022-02-28 19:53
 **/
@Service
public class MiaoshaService {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //减库存 下订单 写入秒杀订单


        //1减库存
        int res = goodsService.reduceStock(goods);
        if (res !=0){
            //2下订单
            return orderService.createOrder(user, goods);
        }else{
            return null;
        }

    }

}
