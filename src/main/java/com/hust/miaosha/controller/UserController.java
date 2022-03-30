package com.hust.miaosha.controller;

import com.hust.miaosha.domain.MiaoshaUser;
import com.hust.miaosha.domain.OrderInfo;
import com.hust.miaosha.redis.RedisService;
import com.hust.miaosha.result.CodeMsg;
import com.hust.miaosha.result.Result;
import com.hust.miaosha.service.MiaoshaService;
import com.hust.miaosha.service.MiaoshaUserService;
import com.hust.miaosha.service.OrderService;
import com.hust.miaosha.util.MD5Util;
import com.hust.miaosha.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @program: miaosha1
 * @description:
 * @author: XuJY
 * @create: 2022-03-01 00:09
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;


    @Autowired
    MiaoshaUserService miaoshaUserService;

    @RequestMapping("/info")
    @ResponseBody
    public Result<MiaoshaUser> info(Model model, MiaoshaUser user) {

        MiaoshaUser user1 = miaoshaUserService.getById_1(user.getId());
        if (user1==null) return Result.error(CodeMsg.SERVER_ERROR);

        return Result.success(user1);
    }

    /**
     * c查询订单
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/myOrders")
    @ResponseBody
    public Result<List<OrderInfo>> infoMyOrder(Model model, Long id, MiaoshaUser user) {
        if (user == null){
            return Result.error(CodeMsg.SERVER_ERROR);
        }

        List<OrderInfo> orderList = orderService.getOrderListByUid(user.getId());
        if (orderList.size()==0){
            return Result.error(CodeMsg.NO_ORDER);
        }

        return Result.success(orderList);






    }


    /**
     * 时序图用：展示个人信息
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/userInfo")
    @ResponseBody
    public Result<MiaoshaUser> info(Model model,long id) {
        MiaoshaUser user = userService.getById_1(id);
        model.addAttribute("user",user);
        return Result.success(user);
    }

    /**
     * 修改个人信息
     * @param model
     * @param
     * @return
     */
    @RequestMapping("/updateInfo")
    @ResponseBody
    public Result<String> updateUserInfo(Model model, UserVo userVo,MiaoshaUser user) {
        model.addAttribute("user",user);
        int update = userService.update(userVo);
        return Result.success("更新成功");
    }


    /**
     * 修改密码
     * @param model
     * @param
     * @return
     */
    @RequestMapping("/updatePassword")
    @ResponseBody
    public Result<String> updatePassword(Model model, UserVo userVo,MiaoshaUser user) {

        String password = userVo.getPassword();
        System.out.println(userVo.toString());
        String dbPassword = MD5Util.inputPassToFormPass(password);


        boolean update = userService.updatePassword("",user.getId(),dbPassword);
        return Result.success("更新成功");
    }


}