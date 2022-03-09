package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.vo.LoginVo;
import com.imooc.miaosha.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

	public static Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	MiaoshaUserService miaoshaUserService;

	@Autowired
	RedisService redisService;

	@RequestMapping("/to_login")
	public String toLogin() {
		return "login";
	}

	@RequestMapping("/do_login")
	@ResponseBody
	public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {//@valid  参数校验
		//登录
		String token =  miaoshaUserService.login(response,loginVo);
		return Result.success(token);

	}

	@RequestMapping("/to_register")
	public String toRegister() {
		return "register";
	}

	@RequestMapping(value = "/register",method = RequestMethod.POST)
	@ResponseBody
	public Result<String> register(UserVo userVo){


		int res = miaoshaUserService.insert(userVo);
		if (res == 1) return Result.success("插入成功");
		else return Result.error(CodeMsg.SERVER_ERROR);
	}


}
