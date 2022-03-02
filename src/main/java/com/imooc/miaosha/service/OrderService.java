package com.imooc.miaosha.service;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleFetchStatement;
import com.imooc.miaosha.dao.OrderDao;
import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @program: miaosha1
 * @description:
 * @author: XuJY
 * @create: 2022-02-28 19:49
 **/
@Service
public class OrderService {

    @Resource
    OrderDao orderDao;



    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long usereId, long goodsId) {

        return orderDao.getMiaoshaOrderByUserIdGoodsId(usereId,goodsId);
    }

    @Transactional//事务，原子操作这个方法
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {

        //完善订单信息
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());

        long orderId = orderDao.insertOrder(orderInfo);

        //完善miaoshaOrder信息
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setUserId(user.getId());

        orderDao.insertMiaoshaOrder(miaoshaOrder);

        return orderInfo;

    }
}
