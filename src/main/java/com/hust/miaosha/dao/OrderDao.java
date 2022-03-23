package com.hust.miaosha.dao;

import com.hust.miaosha.domain.GroupOrder;
import com.hust.miaosha.domain.MiaoshaOrder;
import com.hust.miaosha.domain.OrderInfo;
import com.hust.miaosha.vo.HoutaiOrdersVo;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @program: miaosha1
 * @description:
 * @author: XuJY
 * @create: 2022-02-28 19:41
 **/
@Mapper
public interface OrderDao {

    @Select("select * from miaosha_order mo where mo.user_id = #{userId} and mo.goods_id = #{goodsId}")
    MiaoshaOrder  getMiaoshaOrderByUserIdGoodsId(@Param("userId") long usereId, @Param("goodsId") long goodsId);

    /**
     * 返回值是该订单的orderId
     * @param orderInfo
     * @return
     */
    @Insert("insert into order_info(address,user_id, goods_id, goods_name,goods_img, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{address},#{userId}, #{goodsId}, #{goodsName}, #{goodsImg},#{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    long insertOrder(OrderInfo orderInfo);

    @Insert("insert into miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    @Insert("insert into tuangou_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    int insertTuangouOrder(GroupOrder groupOrder);

    @Select("select * from order_info where id = #{orderId}")
    OrderInfo getOrderById(long orderId);

    @Delete("delete from order_info")
    void deleteOrders();

    @Delete("delete from miaosha_order")
    void deleteMiaoshaOrders();

    @Select("select * from order_info where user_id = #{userId}")
    List<OrderInfo> getOrderListByUid(@PathVariable("userId") Long userId);


    //时序图
    void updateOrders();

    //时序图
    void updateMiaoshaOrders();
/**
 * sc
 *
 */
//查询秒杀订单信息
@Select("select  oi.* ,mo.id as uid  from miaosha_order mo  left join order_info oi on oi.id= mo.order_id    ")
List<HoutaiOrdersVo> houtaiOrderInfo();
    //查询秒杀订单信息通过秒杀id
    @Select("select  oi.* ,mo.id as uid  from miaosha_order mo  left join order_info oi on oi.id= mo.order_id  where oi.id = #{id}")
    HoutaiOrdersVo houtaiOrderInfoById(@Param("id") long id);
    //通过order_id更新订单信息
    @Update("Update order_info set user_id= #{user_id}, goods_id= #{goods_id}, goods_name= #{goods_name}, goods_count= #{goods_count}, goods_price= #{goods_price}, order_channel= #{order_channel}, status= #{status}, create_date= #{create_date}")
    void updateOrderInfo(OrderInfo orderInfo);
    //通过order_id 更新订单信息
    @Update("Update miaosha_order set user_id= #{user_id}, goods_id= #{goods_id}, id= #{id}")
    void updateMiaoshaOrder(MiaoshaOrder miaoshaOrder);
    //更新团购订单
    @Update("Update tuangou_order set user_id= #{user_id}, goods_id= #{goods_id}, id= #{id}")
    void updateTuangouOrder(MiaoshaOrder miaoshaOrder);
    //通过order_id 删除订单信息
    @Delete("delete from order_info where id= #{id}")
    int deleteOrdersById(@Param("id") long id);
    //通过order_id 删除秒杀订单信息
    @Delete("delete from miaosha_order where order_id= #{orderId}")
    int deleteMiaoshaOrdersById( @Param ("orderId") long orderId);
    //通过order_id 删除团购订单信息
    @Delete("delete from tuangou_order where order_id= #{orderId}")
    int deleteTuangouOrdersById( @Param ("orderId") long orderId);
    //查询团购订单信息
    @Select("select  oi.* ,mo.id as uid  from tuangou_order mo  left join order_info oi on oi.id= mo.order_id    ")
    List<HoutaiOrdersVo> houtaiOrderInfo1();
    //查询团购订单信息通过团购id
    @Select("select  oi.* ,mo.id as uid  from tuangou_order mo  left join order_info oi on oi.id= mo.order_id  where oi.id = #{id}")
    HoutaiOrdersVo houtaiOrderInfoById1(@Param("id") long id);
}
