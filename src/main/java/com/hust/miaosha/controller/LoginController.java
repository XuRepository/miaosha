package com.hust.miaosha.controller;

import com.hust.miaosha.redis.RedisService;
import com.hust.miaosha.result.CodeMsg;
import com.hust.miaosha.result.Result;
import com.hust.miaosha.service.MiaoshaUserService;
import com.hust.miaosha.vo.LoginVo;
import com.hust.miaosha.vo.UserVo;
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
