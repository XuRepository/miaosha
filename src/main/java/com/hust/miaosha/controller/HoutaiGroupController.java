package com.hust.miaosha.controller;

import com.hust.miaosha.domain.Group;
import com.hust.miaosha.domain.MiaoshaUser;
import com.hust.miaosha.redis.RedisService;
import com.hust.miaosha.result.Result;
import com.hust.miaosha.service.HoutaiGroupService;
import com.hust.miaosha.service.MiaoshaUserService;
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
 * @Date 2022/3/22
 */
@Controller
@RequestMapping("/houtaiGroup")
public class HoutaiGroupController {

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;
    @Autowired
    HoutaiGroupService houtaiGroupService;

    @RequestMapping("/query")
    @ResponseBody
    public Result<List<Group>> info(Model model) {
        List<Group> groups = houtaiGroupService.queryAll();
        return Result.success(groups);
    }


    /**
     * 时序图用：展示个人信息
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/queryById")
    @ResponseBody
    public Result<Group> queryById(Model model,Long id) {
        Group group= houtaiGroupService.queryById(id);
        model.addAttribute("group",group);
        return Result.success(group);
    }

    /**
     * 时序图用：修改信息
     * @param model
     * @param
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Result<String> update(Model model, Group group) {
        int update = houtaiGroupService.update(group);
        return Result.success("更新成功");
    }


    /**
     * 时序图用：删除信息
     * @param model
     * @param
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Result<String> delete(Model model, Long id) {
        int update = houtaiGroupService.delete(id);
        return Result.success("删除成功");
    }


}