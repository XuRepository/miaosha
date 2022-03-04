package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.redis.keyPrefix.MiaoshaKey;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //减库存 下订单 写入秒杀订单


        //1减库存
        int res = goodsService.reduceStock(goods);
        if (res !=0){
            //2下订单
            return orderService.createOrder(user, goods);
        }else{
            //减库存失败了！秒杀已经卖光了说明，做一个标记，标记商品卖光这一情况。
            setGoodsOver(goods.getId());
            return null;
        }
    }



    //从redis中查询订单，判断秒杀是否成功
    public long getMiaoshaResult(Long userId, long goodsId) {

        MiaoshaOrder miaoshaOrderByUserIdGoodsId = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        if (miaoshaOrderByUserIdGoodsId != null){//秒杀成功！redis有订单了
            return miaoshaOrderByUserIdGoodsId.getOrderId();
        }else{
            if (getGoodsOver(goodsId)){//卖完了，秒杀失败 -1
                return -1;
            }else{
                return 0;//还没卖完  0  还在排队等待
            }
        }

    }

    //在redis中设置商品已卖完！
    private void setGoodsOver(long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver,""+goodsId,true);
    }

    //获取商品是否卖光
    private boolean getGoodsOver(long goodsId) {
        return redisService.exits(MiaoshaKey.isGoodsOver,""+goodsId );
    }

    //重置
    public void reset(List<GoodsVo> goodsList) {
        goodsService.resetStock(goodsList);
        orderService.deleteOrders();
    }
}
