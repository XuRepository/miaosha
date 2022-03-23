package com.hust.miaosha.service;

import com.hust.miaosha.dao.OrderDao;
import com.hust.miaosha.domain.MiaoshaOrder;
import com.hust.miaosha.domain.OrderInfo;
import com.hust.miaosha.redis.RedisService;
//import com.hust.miaosha.util.ClassCopyUtil;
import com.hust.miaosha.util.ClassCopyUtil;
import com.hust.miaosha.vo.HoutaiOrdersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author shucheng
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2022/3/19
 */
@Service("houtaiMiaoshaOrderService")
@Transactional(propagation = Propagation.REQUIRED)
public class HoutaiMiaoshaOrderSerivice {
    @Resource
    OrderDao orderDao;


    @Autowired
    RedisService redisService;

    public HoutaiOrdersVo info( long orderId) {

          return orderDao.houtaiOrderInfoById(orderId);
    }

    /**
     * 删除订单
     * @param orderId
     */

    public void deleteOrders(long orderId) {
        orderDao.deleteOrdersById(orderId);
        orderDao.deleteMiaoshaOrdersById(orderId);
        orderDao.deleteMiaoshaOrders();
    }

    /**
     * 修改订单
     */
    public void updateOrder(HoutaiOrdersVo houtaiOrdersVo) {
        OrderInfo orderInfo = new OrderInfo();
        MiaoshaOrder miaoshaOrder= new MiaoshaOrder();
        ClassCopyUtil classCopyUtil = new ClassCopyUtil();
        try {
            classCopyUtil.reflectionAttr(houtaiOrdersVo,orderInfo);
            classCopyUtil.reflectionAttr(houtaiOrdersVo,miaoshaOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        miaoshaOrder.setOrderId(houtaiOrdersVo.getId());
        miaoshaOrder.setId(houtaiOrdersVo.getUid());
        orderDao.updateOrderInfo(orderInfo);
        orderDao.updateMiaoshaOrder(miaoshaOrder);
    }
    /**
     * 查询所有订单
     *
     */
    public List<HoutaiOrdersVo> queryOrders(){

      return orderDao.houtaiOrderInfo();
    }

    }






