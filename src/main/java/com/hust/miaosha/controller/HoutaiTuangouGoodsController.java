package com.hust.miaosha.controller;

import com.hust.miaosha.domain.MiaoshaUser;
import com.hust.miaosha.redis.RedisService;
import com.hust.miaosha.result.Result;
import com.hust.miaosha.service.HoutaiMiaoshaOrderSerivice;
import com.hust.miaosha.service.HoutaiTuangouGoodsService;
import com.hust.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Description
 * @Author shucheng
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2022/3/22
 */
@Controller
@RequestMapping("/houtaiTuangouGoods")
public class HoutaiTuangouGoodsController {


    @Autowired
    RedisService redisService;

    @Autowired
    HoutaiTuangouGoodsService goodsService;
    @Autowired
    HoutaiMiaoshaOrderSerivice houtaiOrderSerivice;

    @RequestMapping("/query")
    @ResponseBody
    public Result<List<GoodsVo>> list(Model model,MiaoshaUser user) {
        model.addAttribute("user", user);
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return Result.success(goodsList);
    }


    @RequestMapping("/queryById")
    @ResponseBody
    public Result<GoodsVo> info(Model model,Long id) {
//        if(user == null) {
//            return Result.error(CodeMsg.SESSION_ERROR);
//        }

        GoodsVo goods = goodsService.getGoodsVoByGoodsId1(id);

        return Result.success(goods);
    }

    /**
     * 删
     * @param
     * @return
     */
    @RequestMapping("/deleteById")
    @ResponseBody
    public Result<String> deleteOrder(Model model,Long id) {
       int res=  goodsService.delete(id);
        return Result.success("yes");

    }

    /**
     * 改
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Result<String> update(Model model ,GoodsVo goodsVo) {
        int res = goodsService.update(goodsVo);
        return Result.success("yes");

    }
    /**
     * 增
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Result<String> add(MultipartFile photo, GoodsVo goodsVo, HttpServletRequest request) throws IOException {
        //获取文件存储的真实路径
        String realPath = request.getSession().getServletContext().getRealPath("photo");
        //文件拷贝
        photo.transferTo(new File(realPath,photo.getOriginalFilename()));
        //商品的图片名
        goodsVo.setGoodsImg(photo.getOriginalFilename());
        int res =goodsService.insert(goodsVo);
        return Result.success("yes");

    }
}
