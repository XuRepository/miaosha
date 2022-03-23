package com.hust.miaosha.service;

import com.hust.miaosha.dao.GoodsDao;
import com.hust.miaosha.domain.Goods;
import com.hust.miaosha.domain.MiaoshaGoods;
import com.hust.miaosha.redis.RedisService;
import com.hust.miaosha.util.ClassCopyUtil;
import com.hust.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author shucheng
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2022/3/22
 */
@Service("houtaiMiaoshaGoodsService")
public class HoutaiMiaoshaGoodsService {

    @Resource
    GoodsDao goodsDao;

    @Autowired
    RedisService redisService;

    /**
     * 查
     * @return
     */

    public List<GoodsVo> listGoodsVo() {
        List<GoodsVo> list = goodsDao.listGoodsVo();
        return list;
    }

    /**
     * 查根据id
     * @param id
     * @return
     */
    public GoodsVo getGoodsVoById(long id) {
        return goodsDao.getGoodsVoById(id);

    }

    /**
     * 改
     * @param goods
     * @return
     */

    public int update(GoodsVo goods) {
        MiaoshaGoods miaoshaGoods = new MiaoshaGoods();

        Goods  goods1 = new Goods();
        ClassCopyUtil classCopyUtil = new ClassCopyUtil();
        try {
            classCopyUtil.reflectionAttr(goods,goods1);
            classCopyUtil.reflectionAttr(goods,miaoshaGoods);
        } catch (Exception e) {
            e.printStackTrace();
        }

        goods1.setId(goods.getId());
        miaoshaGoods.setGoodsId(goods.getId());
        goodsDao.updateGoods(goods1);
        int res = goodsDao.updateMiaoshaGoods(miaoshaGoods);
        return res;

    }

    /**
     * 增加
     * @param goods
     * @return
     */
    public int insert(GoodsVo goods){
        MiaoshaGoods miaoshaGoods = new MiaoshaGoods();

        Goods  goods1 = new Goods();
        ClassCopyUtil classCopyUtil = new ClassCopyUtil();
        try {
            classCopyUtil.reflectionAttr(goods,goods1);
            classCopyUtil.reflectionAttr(goods,miaoshaGoods);
        } catch (Exception e) {
            e.printStackTrace();
        }
        miaoshaGoods.setGoodsId(goods.getId());
        goods1.setId(goods.getId());
        int res = goodsDao.insertGoods(goods1);
        goodsDao.insertMiaoshaGoods(miaoshaGoods);
        return res;
    }

    /**
     * 删
     * @param
     * @return
     */
    public int delete(Long id){
        goodsDao.deleteGoodsVoById(id);
        int res = goodsDao.deleteMiaoshaGoodsVoById(id);
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
