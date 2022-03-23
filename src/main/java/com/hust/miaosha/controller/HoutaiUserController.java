package com.hust.miaosha.controller;

import com.hust.miaosha.domain.MiaoshaUser;
import com.hust.miaosha.redis.RedisService;
import com.hust.miaosha.result.CodeMsg;
import com.hust.miaosha.result.Result;
import com.hust.miaosha.service.HoutaiUserService;
import com.hust.miaosha.service.MiaoshaUserService;
import com.hust.miaosha.vo.UserVo;
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
@RequestMapping("/houtaiUsers")
public class HoutaiUserController {
    @Autowired
    MiaoshaUserService userService;

    @Autowired
    HoutaiUserService houtaiUserService;

    @Autowired
    RedisService redisService;

//    @RequestMapping("/info")
//    @ResponseBody
//    public Result<MiaoshaUser> info(Model model, MiaoshaUser user) {
//        return Result.success(user);
//    }


    /**
     * 展示个人信息
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/queryUserById" )
    @ResponseBody
    public Result<MiaoshaUser> info(Model model,long id) {
        MiaoshaUser user = userService.getById_1(id);
        model.addAttribute("user",user);
        return Result.success(user);
    }

    /**
     * 时序图用：修改个人信息
     * @param model
     * @param
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
    /**
     * 删除个人信息
     */

    @RequestMapping("/delete")
    @ResponseBody
    public Result<String> deleteUser(Model model,long id ){
        int delete =houtaiUserService.delete(id);
        if (delete == 1) return Result.success("删除成功");
        else return Result.error(CodeMsg.SERVER_ERROR);
    }
    @RequestMapping(value="/query")
    @ResponseBody
    public List<MiaoshaUser> list(Model model) {

        List<MiaoshaUser> userList = houtaiUserService.query();
        model.addAttribute("userList", userList);
        return userList;
    }


}
