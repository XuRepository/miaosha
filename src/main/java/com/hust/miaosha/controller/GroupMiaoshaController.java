package com.hust.miaosha.controller;

import com.hust.miaosha.domain.*;
import com.hust.miaosha.result.CodeMsg;
import com.hust.miaosha.result.Result;
import com.hust.miaosha.service.*;
import com.hust.miaosha.vo.GoodsDetailVo;
import com.hust.miaosha.vo.GoodsVo;
import com.hust.miaosha.vo.GroupVo;
import com.sun.org.apache.bcel.internal.classfile.Code;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import sun.java2d.windows.GDIRenderer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
@Api("团购秒杀")
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
    @RequestMapping("/groupGround")
    @ResponseBody
    public Result<List<Group>> groupGround(MiaoshaUser user){

        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        List<Group> list =groupMiaoshaService.queryGroup();

        if (list.size()==0){
            return Result.error(CodeMsg.NO_GROUP);
        }
        return Result.success(list);
    }


    /**
     * 创建团队
     * @return
     */
    @RequestMapping("/createGroup")
    @ResponseBody
    @ApiOperation(value="创建团队", notes="创建团队")
    public Result<String> createGroup(MiaoshaUser user){

        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        if (groupMiaoshaService.getGroupByUid(user)!=0){
            return Result.error(CodeMsg.HAVE_GROUP);
        }

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
    public Result<String> joinGroup(@PathVariable("groupId") long groupId, MiaoshaUser user){

        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        if (groupMiaoshaService.getGroupByUid(user)!=0){
            return Result.error(CodeMsg.HAVE_GROUP);
        }

        if (groupMiaoshaService.getGroup(groupId) == null){
            return Result.error(CodeMsg.GROUP_NOT_EXIT);
        }
        //加入队伍
        groupMiaoshaService.joinGroup(user,groupId);

        //更新个人信息
        groupMiaoshaService.updateGroup(groupId);

        return Result.success("加团成功");

    }

//    /**
//     * 获取团队信息
//     * @param groupId
//     * @return
//     */
//    @RequestMapping("/get/{groupId}")
//    @ResponseBody
//    public Result<GroupVo> getGroup(@PathVariable("groupId") long groupId){
//        Group group = groupMiaoshaService.getGroup(groupId);
//        GroupVo groupVo = new GroupVo();
//        groupVo.setGroupId(group.getGroupId());
//        groupVo.setHeadCount(group.getHeadCount());
//        groupVo.setTargetCount(group.getTargetCount());
//
//        groupVo.setDiscount();
//
//        return Result.success(groupVo);
//    }

    /**
     * 获取团队信息
     * @param groupId
     * @return
     */
    @RequestMapping("/get")
    @ResponseBody
    public Result<GroupVo> getGroupByUser(MiaoshaUser user){
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        long groupId = groupMiaoshaService.getGroupByUid(user);

        if (groupMiaoshaService.getGroupByUid(user)==0){
            return Result.error(CodeMsg.NO_GROUP);
        }

        Group group = groupMiaoshaService.getGroup(groupId);
        GroupVo groupVo = new GroupVo();

        groupVo.setGroupId(group.getGroupId());
        groupVo.setHeadCount(group.getHeadCount());
        groupVo.setTargetCount(group.getTargetCount());

        double head = 1.0*group.getHeadCount();
        double target = 1.0*group.getTargetCount();
        double discount = 1.0-head/target;

        groupVo.setDiscount(discount);

        return Result.success(groupVo);
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
        double discount = group.getDiscount();
        goods.setDiscount(discount);


        // 减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = groupMiaoshaService.miaosha(user, goods);
        return Result.success(orderInfo);
    }

    @RequestMapping(value="/to_list")
    @ResponseBody
    public List<GoodsVo> list(Model model, MiaoshaUser user) {
        model.addAttribute("user", user);
        List<GoodsVo> goodsList = groupMiaoshaService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return goodsList;
    }


    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user,
                                        @PathVariable("goodsId") long goodsId) {
        GoodsVo goods = groupMiaoshaService.getGoodsVoByGoodsId(goodsId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if (now < startAt) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setMiaoshaStatus(miaoshaStatus);

//        System.out.println(vo.toString());

        return Result.success(vo);
    }





}
