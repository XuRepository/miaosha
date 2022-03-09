package com.hust.miaosha.controller;

import com.hust.miaosha.domain.MiaoshaUser;
import com.hust.miaosha.redis.RedisService;
import com.hust.miaosha.result.Result;
import com.hust.miaosha.service.MiaoshaUserService;
import com.hust.miaosha.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping("/updateInfo")
    @ResponseBody
    public Result<String> updateUserInfo(Model model, UserVo userVo) {
        int update = userService.update(userVo);
        return Result.success("更新成功");
    }


    /**
     * 时序图用：修改密码
     * @param model
     * @param
     * @return
     */
    @RequestMapping("/updatePassword")
    @ResponseBody
    public Result<String> updatePassword(Model model, UserVo userVo) {
        boolean update = userService.updatePassword("",1L,"qqqqqqq");
        return Result.success("更新成功");
    }


}