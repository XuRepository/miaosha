package com.hust.miaosha.controller;

import com.hust.miaosha.domain.MiaoshaOrder;
import com.hust.miaosha.domain.MiaoshaUser;
import com.hust.miaosha.rabbitMQ.MQSender;
import com.hust.miaosha.rabbitMQ.MiaoshaMessage;
import com.hust.miaosha.redis.RedisService;
import com.hust.miaosha.redis.keyPrefix.GoodsKey;
import com.hust.miaosha.redis.keyPrefix.MiaoshaKey;
import com.hust.miaosha.redis.keyPrefix.OrderKey;
import com.hust.miaosha.result.CodeMsg;
import com.hust.miaosha.result.Result;
import com.hust.miaosha.service.GoodsService;
import com.hust.miaosha.service.MiaoshaService;
import com.hust.miaosha.service.MiaoshaUserService;
import com.hust.miaosha.service.OrderService;
import com.hust.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @program: miaosha1
 * @description: 秒杀
 * @author: XuJY
 * @create: 2022-02-28 19:32
 **/
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    MQSender sender;

    //内存标记，判断商品是否已经被秒杀结束，避免重复访问redis造成的开销，long-goodsId   booolean-是否秒杀完成
    private HashMap<Long, Boolean> isOverMap =  new HashMap<Long, Boolean>();

    //系统初始化,把商品库存加入到redis缓存
    @Override
    public void afterPropertiesSet() throws Exception {

        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if (goodsList==null){
            return;
        }
        for (GoodsVo goodsVo: goodsList){
            redisService.set(GoodsKey.getMiaoshaGoodsStock,""+goodsVo.getId(),goodsVo.getStockCount());
            isOverMap.put(goodsVo.getId(), false);

        }


    }


    /**
     * Post方法  需要向服务端提交表单
     */
    @RequestMapping(value = "/do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> miaosha(Model model, MiaoshaUser user, @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        //内存标记，减少redis访问
        // 获取isOver标记，如果已经秒杀结束，直接返回，这样就不用访问1中的redis！
        Boolean isOver = isOverMap.get(goodsId);
        if (isOver){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        //1，预减少缓存中的库存  redis
        Long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
        if (stock < 0) {
            isOverMap.put(goodsId,true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        // 2，判断是否已经秒杀到了，秒杀订单暂存于 redis
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        //3，发送消息入队
        MiaoshaMessage msg = new MiaoshaMessage();
        msg.setGoodsId(goodsId);
        msg.setUser(user);
        sender.sendMiaoshaMessage(msg);

        return Result.success(0);//排队中  0代表排队成功

        //重复秒杀判断，秒杀操作  放到MQReveiver中进行判断！

        //后面客户都安轮询服务端，看是否出队并且秒杀成功。
    }

    /**
     * 客户都安轮询服务器，看是排队的用户否秒杀成功
     * orderId：成功，返回订单号
     * -1：秒杀失败
     * 0： 排队中
     * */
    @RequestMapping(value="/result", method=RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(Model model,MiaoshaUser user,
                                      @RequestParam("goodsId")long goodsId) {
        model.addAttribute("user", user);
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result  =miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(result);
    }

    /**
     * 还原数据库和map信息 方便重新测试
     * @param model
     * @return
     */
    @RequestMapping(value="/reset", method=RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> reset(Model model) {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        for(GoodsVo goods : goodsList) {
            goods.setStockCount(10);
            redisService.set(GoodsKey.getMiaoshaGoodsStock, ""+goods.getId(), 10);
            isOverMap.put(goods.getId(), false);
        }
        redisService.delete(OrderKey.getMiaoshaOrderByUidGid);
        redisService.delete(MiaoshaKey.isGoodsOver);
        miaoshaService.reset(goodsList);
        return Result.success(true);
    }




        /*

        // 判断库存，库存 < 0 则直接返回
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId); //10个商品，req1 req2
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        // 判断是否已经秒杀到了，秒杀订单暂存于 redis
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        // 减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        if (orderInfo!=null)
            return Result.success(orderInfo);
        else
            return Result.error(CodeMsg.MIAO_SHA_OVER);
    }

         */

//    //get
//    @RequestMapping("/do_miaosha")
//    public String miaosha(Model model, MiaoshaUser user, @RequestParam("goodsId") long goodsId) {
//
//        // 判断用户是否为空，用户若为空，则返回登录页面
//        model.addAttribute("user", user);
//        if (user == null) {
//            return "login";
//        }
//
//        //判断库存
//        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
//        int stock = goods.getStockCount();
//        if (stock <= 0) {
//            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
//            return "miaosha_fail";
//        }
//
//        //判断是否已经秒杀到了，避免重复秒杀（判断订单是否生成）
//        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
//        if (order != null) {//已经生成过订单，重复秒杀的情况
//            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
//            return "miaosha_fail";
//        }
//
//        //减库存 下订单 写入秒杀订单
//        //事务  原子操作！！
//        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
//        model.addAttribute("orderInfo", orderInfo);
//        model.addAttribute("goods", goods);
//        return "order_detail";
//    }

}


