package com.hust.miaosha.service;

import com.hust.miaosha.dao.OrderDao;
import com.hust.miaosha.domain.MiaoshaOrder;
import com.hust.miaosha.domain.MiaoshaUser;
import com.hust.miaosha.domain.OrderInfo;
import com.hust.miaosha.redis.RedisService;
import com.hust.miaosha.redis.keyPrefix.OrderKey;
import com.hust.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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


    @Autowired
    RedisService redisService;


    //从redis取唯一的订单进行查询，防止卖超
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {

//        return orderDao.getMiaoshaOrderByUserIdGoodsId(usereId,goodsId);
        return redisService.get(OrderKey.getMiaoshaOrderByUidGid, "" + userId + "_" + goodsId, MiaoshaOrder.class);

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

        orderInfo.setGoodsImg(goods.getGoodsImg());
        orderInfo.setAddress(user.getAddress());


        orderDao.insertOrder(orderInfo);

        //先生成完成的orderInfo，他的id是自增的
        //在生成miaoshaOrder
        //秒杀orer的OrderId为orderinfo的id

        //完善miaoshaOrder信息
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(user.getId());

        orderDao.insertMiaoshaOrder(miaoshaOrder);

        //写入redis，防止卖超
        redisService.set(OrderKey.getMiaoshaOrderByUidGid, "" + user.getId() + "_" + goods.getId(), miaoshaOrder);

        return orderInfo;

    }

    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }

    /**
     * 重置
     */
    public void deleteOrders() {
        orderDao.deleteOrders();
        orderDao.deleteMiaoshaOrders();
    }
    /**
     * 时序图：删除订单
     */
    public void deleteOrders1(MiaoshaUser user) {
        redisService.delete(OrderKey.getMiaoshaOrderByUidGid, "" + user.getId());
        orderDao.deleteOrders();
        orderDao.deleteMiaoshaOrders();
    }
    /**
     * 时序图：修改订单
     */
    public void updateOrder(MiaoshaUser user,MiaoshaOrder order) {
        redisService.set(OrderKey.getMiaoshaOrderByUidGid, "" + user.getId(),order);
        orderDao.updateOrders();
        orderDao.updateMiaoshaOrders();
    }


    /**
     * 根据用户id查询
     * @return
     */
    public List<OrderInfo> getOrderListByUid(Long userId) {


        return orderDao.getOrderListByUid(userId);

    }
}
