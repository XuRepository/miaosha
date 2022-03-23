package com.hust.miaosha.service;

import com.hust.miaosha.dao.GoodsDao;
import com.hust.miaosha.domain.MiaoshaGoods;
import com.hust.miaosha.redis.RedisService;
import com.hust.miaosha.vo.GoodsVo;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public GoodsVo getGoodsVoByGoodsId1(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId1(goodsId);
    }

    /**
     * 重置
     * @param goodsList
     */
    public void resetStock(List<GoodsVo> goodsList) {
        for(GoodsVo goods : goodsList ) {
            MiaoshaGoods g = new MiaoshaGoods();
            g.setGoodsId(goods.getId());
            g.setStockCount(goods.getStockCount());
            goodsDao.resetStock(g);
        }
    }
}
