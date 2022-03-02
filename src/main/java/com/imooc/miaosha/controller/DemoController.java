package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.redis.keyPrefix.UserKey;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/demo")
public class DemoController {

	@Autowired
	UserService userService;

	@Autowired
	RedisService redisService;

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}


	//1.rest api json输出 2.页面
	@RequestMapping("/hello")
	@ResponseBody
	public Result<String> hello() {
		return Result.success("hello,imooc");
		// return new Result(0, "success", "hello,imooc");
	}

	@RequestMapping("/helloError")
	@ResponseBody
	public Result<String> helloError() {
		return Result.error(CodeMsg.SERVER_ERROR);
		//return new Result(500102, "XXX");
	}

	@RequestMapping("/thymeleaf")
	public String thymeleaf(Model model) {
		model.addAttribute("name", "Joshua");
		return "hello";
	}

	@RequestMapping("/db/get")
	@ResponseBody
	public Result<User> dbSelect() {
		return Result.success(userService.getByID(1));
	}

	@RequestMapping("/db/insert")
	@ResponseBody
	public Result<Integer> dbInsert() {
		User user = new User(2, "haha");

		return Result.success(userService.insert(user));
	}

	@RequestMapping("/redis/get")
	@ResponseBody
	public Result<Integer> redisGet() {

		Integer res = redisService.get(UserKey.getById,"key1", Integer.class);

		return Result.success(res);
	}

	@RequestMapping("/redis/set")
	@ResponseBody
	public Result<Boolean> redisSet() {

		boolean res = redisService.set(UserKey.getById,"key2", "hahahah");

		return Result.success(res);

	}
}
