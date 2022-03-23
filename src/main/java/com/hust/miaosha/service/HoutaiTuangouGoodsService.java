package com.hust.miaosha.service;

import com.hust.miaosha.dao.GoodsDao;
import com.hust.miaosha.domain.Goods;
import com.hust.miaosha.domain.TuangouGoods;
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
@Service("houtaiTuangouGoodsService")
public class HoutaiTuangouGoodsService {

    @Resource
    GoodsDao goodsDao;

    @Autowired
    RedisService redisService;

    /**
     * 查
     * @return
     */

    public List<GoodsVo> listGoodsVo() {
        List<GoodsVo> list = goodsDao.listGoodsVo1();
        return list;
    }

    /**
     * 查根据id
     * @param id
     * @return
     */
    public GoodsVo getGoodsVoById(long id) {
        return goodsDao.getGoodsVoById1(id);

    }

    /**
     * 改
     * @param goods
     * @return
     */

    public int update(GoodsVo goods) {
        TuangouGoods tuangouGoods = new TuangouGoods();
        tuangouGoods.setGoodsId(goods.getId());
        Goods  goods1 = new Goods();
        ClassCopyUtil classCopyUtil = new ClassCopyUtil();
        try {
            classCopyUtil.reflectionAttr(goods,goods1);
            classCopyUtil.reflectionAttr(goods,tuangouGoods);
        } catch (Exception e) {
            e.printStackTrace();
        }


        goods1.setId(goods.getId());
        goodsDao.updateGoods(goods1);
        int res = goodsDao.updateTuangouGoods(tuangouGoods);
        return res;

    }

    /**
     * 增加
     * @param goods
     * @return
     */
    public int insert(GoodsVo goods){
        TuangouGoods tuangouGoods = new TuangouGoods();

        Goods  goods1 = new Goods();
        ClassCopyUtil classCopyUtil = new ClassCopyUtil();
        try {
            classCopyUtil.reflectionAttr(goods,goods1);
            classCopyUtil.reflectionAttr(goods,tuangouGoods);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tuangouGoods.setGoodsId(goods.getId());
        goods1.setId(goods.getId());
        int res = goodsDao.insertGoods(goods1);
        goodsDao.insertTuangouGoods(tuangouGoods);
        return res;
    }

    /**
     * 删
     * @param
     * @return
     */
    public int delete(Long  id){
        goodsDao.deleteGoodsVoById(id);
        int res = goodsDao.deleteTuangouGoodsVoById(id);
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
            TuangouGoods g = new TuangouGoods();
            g.setGoodsId(goods.getId());
            g.setStockCount(goods.getStockCount());
            goodsDao.resetStock1(g);
        }
    }

}
