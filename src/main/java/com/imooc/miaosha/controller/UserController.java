package com.imooc.miaosha.controller;

import com.imooc.miaosha.dao.UserDao;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.util.MD5Util;
import com.imooc.miaosha.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

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

    @RequestMapping("/info")
    @ResponseBody
    public Result<MiaoshaUser> info(Model model, MiaoshaUser user) {
        return Result.success(user);
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
     * 时序图用：修改个人信息
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/userInfo")
    @ResponseBody
    public Result<String> updateUserInfo(Model model, UserVo userVo) {
        int update = userService.update(userVo);
        return Result.success("更新成功");
    }



}