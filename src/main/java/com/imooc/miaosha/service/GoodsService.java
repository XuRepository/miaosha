package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.GoodsDao;
import com.imooc.miaosha.dao.MiaoshaUserDao;
import com.imooc.miaosha.domain.MiaoshaGoods;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.exception.GlobalException;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.redis.keyPrefix.MiaoshaUserKey;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.util.MD5Util;
import com.imooc.miaosha.util.UUIDUtil;
import com.imooc.miaosha.vo.GoodsVo;
import com.imooc.miaosha.vo.LoginVo;
import groovy.util.logging.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @program: miaosha1
 * @description:
 * @author: XuJY
 * @create: 2022-02-27 15:54
 **/
@Service
@Slf4j
public class GoodsService {

    Logger logger = LoggerFactory.getLogger(GoodsService.class);

    @Resource
    GoodsDao goodsDao;

    @Autowired
    RedisService redisService;


    public List<GoodsVo> listGoodsVo() {
        List<GoodsVo> list = goodsDao.listGoodsVo();
        return list;
    }


    public GoodsVo getGoodsVoById(long id) {
        return goodsDao.getGoodsVoById(id);

    }

    public int reduceStock(GoodsVo goods) {
        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goods.getId());
        // 减库存操作依赖数据库去完成，将 "卖超问题" 交给数据库层面的乐观锁去控制
        int res = goodsDao.reduceStock(g);
        return res;


    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }
}
