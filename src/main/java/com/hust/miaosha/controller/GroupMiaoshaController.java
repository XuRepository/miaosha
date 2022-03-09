package com.hust.miaosha.controller;

import com.hust.miaosha.domain.Group;
import com.hust.miaosha.domain.MiaoshaGoods;
import com.hust.miaosha.domain.MiaoshaUser;
import com.hust.miaosha.result.Result;
import com.hust.miaosha.service.GroupMiaoshaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    /**
     * 创建团队
     * @return
     */
    @RequestMapping("/createGroup")
    public Result<String> createGroup(MiaoshaUser user){

        groupMiaoshaService.createGroup(user);
        return Result.success("建团成功");
    }

    /**
     * 加入别人的团队
     * @param groupId
     * @return
     */
    @RequestMapping("/joinGroup")
    public Result<String> joinGroup(long groupId,MiaoshaUser miaoshaUser){


        groupMiaoshaService.joinGroup(miaoshaUser,groupId);

        return Result.success("加团成功");
    }

    /**
     *
     * @return
     */
    @RequestMapping("/miaoshaGroup")
    public Result<String> groupMiaosha(MiaoshaUser miaoshaUser, MiaoshaGoods goods){
        //获取团队信息，获取秒杀折扣
        Group group = groupMiaoshaService.getGroup(miaoshaUser);

        groupMiaoshaService.groupMiaosha(miaoshaUser,group,goods);

        return Result.success("秒杀成功");
    }




}
