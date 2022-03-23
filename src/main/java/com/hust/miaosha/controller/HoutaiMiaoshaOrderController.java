package com.hust.miaosha.controller;

import com.hust.miaosha.domain.MiaoshaUser;
import com.hust.miaosha.redis.RedisService;
import com.hust.miaosha.result.CodeMsg;
import com.hust.miaosha.result.Result;
import com.hust.miaosha.service.GoodsService;
import com.hust.miaosha.service.HoutaiMiaoshaOrderSerivice;
import com.hust.miaosha.service.MiaoshaUserService;
import com.hust.miaosha.service.OrderService;
import com.hust.miaosha.vo.HoutaiOrdersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description
 * @Author shucheng
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2022/3/20
 */
@Controller
@RequestMapping("/houtaiMiaoshaOrders")
public class HoutaiMiaoshaOrderController {

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;
    @Autowired
    HoutaiMiaoshaOrderSerivice houtaiOrderSerivice;
    @Autowired
    GoodsService goodsService;
    @RequestMapping("/query")
    @ResponseBody
    public Result<List<HoutaiOrdersVo>> infoAll(Model model) {
//        model.addAttribute("user", user);
        List<HoutaiOrdersVo> houtaiOrdersVoList = houtaiOrderSerivice.queryOrders();
//        System.out.println(redisService.get(OrderKey.getMiaoshaOrderByUidGid, ""  + "_" , MiaoshaOrder.class));
        model.addAttribute("houtaiOrdersVoList", houtaiOrdersVoList);
        return Result.success(houtaiOrdersVoList);
    }

    @RequestMapping("/queryById")
    @ResponseBody
    public Result<HoutaiOrdersVo> info(Model model,Long orderId) {
//        if(user == null) {
//            return Result.error(CodeMsg.SESSION_ERROR);
//        }

        HoutaiOrdersVo order = houtaiOrderSerivice.info(orderId);
        if(order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }

        return Result.success(order);
    }

    /**
     * 时序图
     * @param model
     * @param
     * @param orderId
     * @return
     */
    @RequestMapping("/deleteById")
    @ResponseBody
    public Result<String> deleteOrder(Model model, Long orderId) {
        houtaiOrderSerivice.deleteOrders(orderId);
        return Result.success("yes");

    }

    @RequestMapping("/updateOrder")
    @ResponseBody
    public Result<String> updateOrder(Model model ,HoutaiOrdersVo houtaiOrdersVo) {
        houtaiOrderSerivice.updateOrder(houtaiOrdersVo);
        return Result.success("yes");

    }
}
