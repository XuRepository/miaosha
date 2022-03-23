package com.hust.miaosha.controller;

import com.hust.miaosha.domain.Group;
import com.hust.miaosha.domain.MiaoshaGoods;
import com.hust.miaosha.domain.MiaoshaUser;
import com.hust.miaosha.result.CodeMsg;
import com.hust.miaosha.result.Result;
import com.hust.miaosha.service.GroupMiaoshaService;
import com.hust.miaosha.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: miaosha1
 * @description: 分享功能
 * @author: XuJY
 * @create: 2022-03-09 14:55
 **/
public class ShareMiaoshaController {
    @Autowired
    ShareService shareService;

    /**
     * 分享
     * @return
     */
    @RequestMapping("/share")
    public Result<String> share(MiaoshaUser user,MiaoshaGoods goods){

        shareService.share(user,goods);
        return Result.success("分享成功");
    }

    /**
     *
     * @return
     */
    @RequestMapping("/miaoshaShare")
    public Result<String> shareMiaosha(MiaoshaUser user, MiaoshaGoods goods){
        //获取是否分享
        boolean isShared = shareService.isShared();

        if (isShared){
            shareService.shareMiaosha(user,goods);
            return Result.success("成功");
        }else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }


}
