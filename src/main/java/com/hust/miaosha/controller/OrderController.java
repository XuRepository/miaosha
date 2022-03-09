package com.hust.miaosha.controller;

import com.hust.miaosha.domain.MiaoshaOrder;
import com.hust.miaosha.domain.MiaoshaUser;
import com.hust.miaosha.domain.OrderInfo;
import com.hust.miaosha.redis.RedisService;
import com.hust.miaosha.result.CodeMsg;
import com.hust.miaosha.result.Result;
import com.hust.miaosha.service.GoodsService;
import com.hust.miaosha.service.MiaoshaUserService;
import com.hust.miaosha.service.OrderService;
import com.hust.miaosha.vo.GoodsVo;
import com.hust.miaosha.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: miaosha1
 * @description:
 * @author: XuJY
 * @create: 2022-03-03 19:50
 **/
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, MiaoshaUser user,
                                      @RequestParam("orderId") long orderId) {
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        OrderInfo order = orderService.getOrderById(orderId);
        if(order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }

        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);

        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);

        return Result.success(vo);
    }

    /**
     * 时序图
     * @param model
     * @param user
     * @param orderId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Result<String> deleteOrder(Model model, MiaoshaUser user,
                                 @RequestParam("orderId") long orderId) {
        orderService.deleteOrders1(user);
        return Result.success("yes");

    }

    /**
     * 时序图
     * @param model
     * @param user
     * @param orderId
     * @return
     */
    @RequestMapping("/updateOrder")
    @ResponseBody
    public Result<String> updateOrder(Model model, MiaoshaUser user,
                                 @RequestParam("orderId") long orderId) {
        orderService.updateOrder(user,new MiaoshaOrder());
        return Result.success("yes");

    }



}
