package com.hust.miaosha.rabbitMQ;

import com.hust.miaosha.domain.MiaoshaOrder;
import com.hust.miaosha.domain.MiaoshaUser;
import com.hust.miaosha.redis.RedisService;
import com.hust.miaosha.service.GoodsService;
import com.hust.miaosha.service.MiaoshaService;
import com.hust.miaosha.service.OrderService;
import com.hust.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: miaosha1
 * @description: 消息队列的接收者
 * @author: XuJY
 * @create: 2022-03-04 11:24
 **/
@Service
public class MQReceiver {

    Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    RedisService redisService;

    //接收秒杀订单
    @RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
    public void receive(String message) {

        logger.info("receive message:"+message);
        MiaoshaMessage mm  = redisService.stringToBean(message, MiaoshaMessage.class);
        MiaoshaUser user = mm.getUser();
        long goodsId = mm.getGoodsId();


        //这里访问了MYSQL
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return;
        }

        //判断是否已经秒杀到了   redis
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        miaoshaService.miaosha(user, goods);
    }
//
//
//    /**
//     * direct模式
//     * @param message
//     */
//    @RabbitListener(queues = MQConfig.QUEUE)
//    public void revecie(String message){
//        logger.info("reveive:"+message);
//
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
//    public void receiveTopic1(String message) {
//        logger.info(" topic  queue1 message:" + message);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
//    public void receiveTopic2(String message) {
//        logger.info(" topic  queue2 message:" + message);
//    }

}
