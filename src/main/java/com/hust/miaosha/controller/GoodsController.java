package com.hust.miaosha.controller;

import com.hust.miaosha.domain.MiaoshaUser;
import com.hust.miaosha.redis.RedisService;
import com.hust.miaosha.redis.keyPrefix.GoodsKey;
import com.hust.miaosha.result.Result;
import com.hust.miaosha.service.GoodsService;
import com.hust.miaosha.vo.GoodsDetailVo;
import com.hust.miaosha.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @program: miaosha1
 * @description: 商品
 * @author: XuJY
 * @create: 2022-02-27 21:39
 **/
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;


    //以下的bean用于渲染html
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value="/to_list")
    @ResponseBody
    public List<GoodsVo> list(Model model,MiaoshaUser user) {
        model.addAttribute("user", user);
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return goodsList;
    }

//
//    //页面缓存
//    // MiaoShaUser 的参数化注解！！！UserArgumentResolver
//    @RequestMapping(value = "/to_list1",produces = "text/html")//加上produces，发送给前端指定的类型，在这里返回的是一个html页面,需要和responseBody一起使用
//    @ResponseBody//不加@ResponseBody注解相当于按照和返回String同名jsp页面解析自然就会报错
//    public String list(HttpServletRequest request, HttpServletResponse response,Model model, MiaoshaUser user
////                       @CookieValue(value = MiaoshaUserService.COOKIE_NAME_TOKEN,required = false) String cookieToken,
////                       @RequestParam(value = MiaoshaUserService.COOKIE_NAME_TOKEN,required = false) String paramToken
//    ) {
////
////        //如果请求没有带上cookie，则认为当前请求未登录，转到登录页面。
////        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
////            return "login";
////        }
////        //拿到token，可以查询redis
////        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
//
////        MiaoshaUser user = userService.getByToken(response,token);
//        model.addAttribute("user", user);
//
//        //1，从redis中找缓存，找不到再手动渲染。
//        String html = redisService.get(GoodsKey.getGoodsList,"",String.class);//商品列表只有一个，不需要有key，key为空即可
//        if (!StringUtils.isEmpty(html)){
//            return html;
//        }
//
//        //2，缓存中没有html，那就手动渲染一份html出来！
//        //获取秒杀商品列表
//        List<GoodsVo> list = goodsService.listGoodsVo();
//        model.addAttribute("goodsList",list);
//
//        //手动渲染，springboot-thymleaf，查文档可有看出
//        SpringWebContext ctx = new SpringWebContext(request, response,
//                request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
//        //手动渲染
//        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
//        if (!StringUtils.isEmpty(html)) {
//            redisService.set(GoodsKey.getGoodsList, "", html);//渲染好的纯html页面加入缓存
//        }
//        return html;
//
//    }


    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user,
                                        @PathVariable("goodsId") long goodsId) {
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
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


    //页面缓存
    // MiaoShaUser 的参数化注解！！！UserArgumentResolver
    @RequestMapping(value = "/to_detail2/{goodsId}",produces = "text/html")
    @ResponseBody
    public String to_detail1(HttpServletRequest request, HttpServletResponse response,
                            Model model, MiaoshaUser user,
                            @PathVariable("goodsId")long goodsId) {

        model.addAttribute("user", user);

        //1，从redis中找缓存，找不到再手动渲染。
        String html = redisService.get(GoodsKey.getGoodsDetail,""+goodsId,String.class);//商品列表只有一个，不需要有key，key为空即可
        if (!StringUtils.isEmpty(html)){
            return html;
        }

        //2，缓存中没有html，那就手动渲染一份html出来！
        //获取秒杀商品
        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
        model.addAttribute("goods",goods);

        //判断秒杀超时？
        long start  = goods.getStartDate().getTime();
        long end  = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        //判断秒杀的状态
        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if(now < start ) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)((start - now )/1000);
        }else  if(now > end){//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        //手动渲染，springboot-thymleaf，查文档可有看出
        SpringWebContext ctx = new SpringWebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, ""+goodsId, html);//渲染好的纯html页面加入缓存
        }
        return html;

    }

//    // MiaoShaUser 的参数化注解！！！UserArgumentResolver
//    @RequestMapping(value = "/to_detail/{goodsId}")
//    public String to_detail1(Model model, MiaoshaUser user, @PathVariable("goodsId")long goodsId) {
//        model.addAttribute("user", user);
//
//        //获取秒杀商品列表
//        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
//        model.addAttribute("goods",goods);
//
//        long start  = goods.getStartDate().getTime();
//        long end  = goods.getEndDate().getTime();
//        long now = System.currentTimeMillis();
//
//        //判断秒杀的状态
//        int miaoshaStatus = 0;
//        int remainSeconds = 0;
//        if(now < start ) {//秒杀还没开始，倒计时
//            miaoshaStatus = 0;
//            remainSeconds = (int)((start - now )/1000);
//        }else  if(now > end){//秒杀已经结束
//            miaoshaStatus = 2;
//            remainSeconds = -1;
//        }else {//秒杀进行中
//            miaoshaStatus = 1;
//            remainSeconds = 0;
//        }
//        model.addAttribute("miaoshaStatus", miaoshaStatus);
//        model.addAttribute("remainSeconds", remainSeconds);
//
//        return "goods_detail";
//    }


}
