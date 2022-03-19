package com.hust.miaosha.controller.tuangou;

import com.hust.miaosha.domain.*;
import com.hust.miaosha.result.CodeMsg;
import com.hust.miaosha.result.Result;
import com.hust.miaosha.service.*;
import com.hust.miaosha.vo.GoodsVo;
import com.hust.miaosha.vo.GroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: miaosha1
 * @description: 团购秒杀,-时序图
 * 只有邀请大家组团成功之后才能参与秒杀，团的人数越多，秒杀折扣越高！
 * 例如：不建团，原价。
 * 建团1人，9折
 * 建团2人 8折
 * 。
 * 。
 * 。
 * 建团XX人 XX折
 * @author: XuJY
 * @create: 2022-03-09 13:20
 **/
@Controller
@RequestMapping("/groupMiaosha")
public class GroupMiaoshaController {
    @Autowired
    GroupMiaoshaService groupMiaoshaService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaUserService miaoshaUserService;


    /**
     * 创建团队
     * @return
     */
    @RequestMapping("/createGroup")
    @ResponseBody
    public Result<String> createGroup(MiaoshaUser user){

        groupMiaoshaService.createGroup(user);
        return Result.success("建团成功");
    }

    /**
     * 加入别人的团队
     * @param groupId
     * @return
     */
    @RequestMapping("/joinGroup/{groupId}")
    @ResponseBody
    public Result<String> joinGroup(@PathVariable("groupId") long groupId, MiaoshaUser miaoshaUser){


        groupMiaoshaService.joinGroup(miaoshaUser,groupId);
        groupMiaoshaService.updateGroup(groupId);

        return Result.success("加团成功");
    }

    /**
     * 获取团队信息
     * @param groupId
     * @return
     */
    @RequestMapping("/get/{groupId}")
    @ResponseBody
    public Result<GroupVo> getGroup(@PathVariable("groupId") long groupId){
        Group group = groupMiaoshaService.getGroup(groupId);
        GroupVo groupVo = new GroupVo();
        groupVo.setGroupId(group.getGroupId());
        groupVo.setHeadCount(group.getHeadCount());
        groupVo.setTargetCount(group.getTargetCount());

        groupVo.setDiscount();

        return Result.success(groupVo);
    }

    /**
     *
     * @return
     */
    @RequestMapping("/miaoshaGroup")
    public Result<String> groupMiaosha(MiaoshaUser miaoshaUser, MiaoshaGoods goods){
        //获取团队信息，获取秒杀折扣
        Group group = groupMiaoshaService.getGroup(miaoshaUser.getGroupId());

        groupMiaoshaService.groupMiaosha(miaoshaUser,group,goods);

        return Result.success("秒杀成功");
    }

    /**
     * 团购秒杀
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public Result<OrderInfo> miaosha(Model model, MiaoshaUser user, @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        // 判断库存，库存 < 0 则直接返回
        GoodsVo goods = groupMiaoshaService.getGoodsVoByGoodsId(goodsId); //10个商品，req1 req2
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        // 判断是否已经秒杀到了，秒杀订单暂存于 redis
        MiaoshaOrder order = groupMiaoshaService.getGroupOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        //查询团队人数，获取折扣
        long groupId = groupMiaoshaService.getGroupByUid(user);
        if (groupId == 0){
            return Result.error(CodeMsg.NO_GROUP);
        }
        Group group = groupMiaoshaService.getGroup(groupId);
        int discount = group.getDiscount();
        goods.setDiscount(discount);

        // 减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = groupMiaoshaService.miaosha(user, goods);
        return Result.success(orderInfo);
    }




}
