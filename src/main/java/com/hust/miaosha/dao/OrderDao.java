package com.hust.miaosha.dao;

import com.hust.miaosha.domain.MiaoshaOrder;
import com.hust.miaosha.domain.OrderInfo;
import org.apache.ibatis.annotations.*;

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
    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    long insertOrder(OrderInfo orderInfo);

    @Insert("insert into miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    @Select("select * from order_info where id = #{orderId}")
    OrderInfo getOrderById(long orderId);

    @Delete("delete from order_info")
    void deleteOrders();

    @Delete("delete from miaosha_order")
    void deleteMiaoshaOrders();

    //时序图
    void updateOrders();

    //时序图
    void updateMiaoshaOrders();
}
