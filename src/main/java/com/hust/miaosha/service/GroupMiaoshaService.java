package com.hust.miaosha.service;

import com.hust.miaosha.dao.*;
import com.hust.miaosha.domain.*;
import com.hust.miaosha.redis.RedisService;
import com.hust.miaosha.redis.keyPrefix.OrderKey;
import com.hust.miaosha.vo.GoodsVo;
import com.hust.miaosha.vo.GroupGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @program: miaosha1
 * @description: 团购秒杀服务-时序图
 * @author: XuJY
 * @create: 2022-03-09 13:31
 **/
@Service
public class GroupMiaoshaService {

    @Resource
    GroupDao groupDao;

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    MiaoshaService miaoshaService;

    @Resource
    GoodsDao goodsDao;

    @Autowired
    RedisService redisService;

    @Resource
    MiaoshaUserDao miaoshaUserDao;

    @Resource
    OrderDao orderDao;

    public void createGroup(MiaoshaUser user) {

        groupDao.createGroup(user.getId(),1);
        miaoshaUserService.updateGroup(user.getId(),user.getId(),"");


    }

    public void joinGroup(MiaoshaUser miaoshaUser, long groupId) {
        miaoshaUserService.updateGroup( miaoshaUser.getId(), groupId,"");
    }



    public Group getGroup(long groupId) {
        return groupDao.getGroup(groupId);
    }

    public long getGroupByUid(MiaoshaUser user) {
        if(miaoshaUserDao.getGroupIdById(user.getId())==null) return 0;

        return miaoshaUserDao.getGroupIdById(user.getId());
    }


    public void updateGroup(long groupId) {
        Group group = groupDao.getGroup(groupId);
        group.setHeadCount(group.getHeadCount()+1);
        groupDao.updateHeadCount(group);
    }





    //goodsDao

    public List<GoodsVo> listGoodsVo() {
        List<GoodsVo> list = goodsDao.listGoodsVo1();
        return list;
    }


    public GoodsVo getGoodsVoById(long id) {
        return goodsDao.getGoodsVoById1(id);

    }

    public int reduceStock(GoodsVo goods) {
        TuangouGoods g = new TuangouGoods();
        g.setGoodsId(goods.getId());
        // 减库存操作依赖数据库去完成，将 "卖超问题" 交给数据库层面的乐观锁去控制
        int res = goodsDao.reduceStock1(g);
        return res;
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId1(goodsId);
    }


    //order Dao

    //从redis取唯一的订单进行查询，防止卖超
    public MiaoshaOrder getGroupOrderByUserIdGoodsId(long userId, long goodsId) {

//        return orderDao.getMiaoshaOrderByUserIdGoodsId(usereId,goodsId);
        return redisService.get(OrderKey.getTuangouOrderByUidGid, "" + userId + "_" + goodsId, MiaoshaOrder.class);

    }

    /**
     * 创建订单，先更新数据库，再写缓存
     *
     * @param user
     * @param goods
     * @return
     */
    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());

        orderInfo.setGoodsPrice(goods.getMiaoshaPrice()*goods.getDiscount());

        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());

        long orderId = orderDao.insertOrder(orderInfo);

        GroupOrder groupOrder = new GroupOrder();
        groupOrder.setGoodsId(goods.getId());
        groupOrder.setOrderId(orderId);
        groupOrder.setUserId(user.getId());
        orderDao.insertTuangouOrder(groupOrder);

        redisService.set(OrderKey.getTuangouOrderByUidGid, "" + user.getId() + "_" + goods.getId(), groupOrder);

        return orderInfo;
    }

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //减库存 下订单 写入秒杀订单
        reduceStock(goods);

        //order_info maiosha_order
        return createOrder(user, goods);
    }
}
