package com.hust.miaosha.service;

import com.hust.miaosha.dao.ShareDao;
import com.hust.miaosha.domain.MiaoshaGoods;
import com.hust.miaosha.domain.MiaoshaUser;
import com.hust.miaosha.result.Result;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: miaosha1
 * @description:分享秒杀
 * @author: XuJY
 * @create: 2022-03-09 15:00
 **/
public class ShareService {

    @Autowired
    ShareDao shareDao;

    public Result<String> share(MiaoshaUser user, MiaoshaGoods goods){

        shareDao.shareGoods(user,goods);
        return Result.success("分享成功");
    }

    public boolean isShared() {
        return shareDao.isShared();
    }

    public void shareMiaosha(MiaoshaUser user, MiaoshaGoods goods) {
        shareDao.shareMiaosha(user,goods);
    }
}
