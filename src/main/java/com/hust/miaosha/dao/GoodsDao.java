package com.hust.miaosha.dao;

import com.hust.miaosha.domain.Goods;
import com.hust.miaosha.domain.MiaoshaGoods;
import com.hust.miaosha.domain.TuangouGoods;
import com.hust.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsDao {

    //LEFT JOIN 关键字会从左表 (Persons) 那里返回所有的行，即使在右表 (Orders) 中没有匹配的行。
    @Select("select  g.*, mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    List<GoodsVo> listGoodsVo();

    @Select("select  g.*, mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{id}")
    GoodsVo getGoodsVoById(@Param("id") long id) ;

    @Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count >0")
    int reduceStock(MiaoshaGoods g);


    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    @Select("select g.*,g.goods_img,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId2(@Param("goodsId") long goodsId);

    @Update("update miaosha_goods set stock_count = #{stockCount} where goods_id = #{goodsId}")
    int resetStock(MiaoshaGoods g);




    //tuangou
    //////
    //LEFT JOIN 关键字会从左表 (Persons) 那里返回所有的行，即使在右表 (Orders) 中没有匹配的行。
    @Select("select  g.*, mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from tuangou_goods mg left join goods g on mg.goods_id = g.id")
    List<GoodsVo> listGoodsVo1();

    @Select("select  g.*, mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from tuangou_goods mg left join goods g on mg.goods_id = g.id where g.id = #{id}")
    GoodsVo getGoodsVoById1(@Param("id") long id) ;

    @Update("update tuangou_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count >0")
    int reduceStock1(TuangouGoods g);


    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from tuangou_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId1(@Param("goodsId") long goodsId);

//    @Update("update tuangou_goods set stock_count = #{stockCount} where goods_id = #{goodsId}")
//    int resetStock1(TuangouGoods g);

    /**
     * sc
     */

    //后台秒杀
    //插入商品
    @Insert("insert into goods(id,goods_name,goods_title,goods_img,goods_detail,goods_price,goods_stock) values(#{id},#{goods_name},#{goods_title},#{goods_img},#{goods_detail},#{goods_price},#{goods_stock})")
    public int insertGoods(Goods g);

    //删商品
    @Delete("delete  from goods where id = #{id}")
    int deleteGoodsVoById(@Param("id") long id);
    //改商品
    @Update("update goods set (id =#{id},goods_name=#{goods_name},goods_title=#{goods_title},goods_img=#{goods_img},goods_detail=#{goods_detail},goods_price=#{goods_price},goods_stock=#{goods_stock} where goods_id = #{goodsId}")
    int updateGoods(Goods g);
    //插入秒杀商品
    @Insert("insert into miaosha_goods(id,goods_id,miaosha_price,start_date,end_date) values(#{id},#{goods_id},#{miaosha_price},#{start_date},#{end_date})")
    public int insertMiaoshaGoods(MiaoshaGoods g);
    //改秒杀商品
    @Update("update miaosha_goods set (goods_id=#{goods_id},miaosha_price=#{miaosha_price},start_date=#{start_date},end_date=#{end_date} where goods_id = #{goodsId}")
//    @Update("update miaosha_goods set (id =#{id},goods_id=#{goods_id},miaosha_price=#{miaosha_price},start_date=#{start_date},end_date=#{end_date} where goods_id = #{goodsId}")
    int updateMiaoshaGoods(MiaoshaGoods g);
    //删秒杀商品
    @Delete("delete  from miaosha_goods where id = #{goodsId}")
    int deleteMiaoshaGoodsVoById(@Param("goodsId") long goodsId);
    //团购秒杀
    //插入秒杀商品
    @Insert("insert into Tuangou_goods(id,goods_id,miaosha_price,start_date,end_date) values(#{id},#{goods_id},#{miaosha_price},#{start_date},#{end_date})")
    public int insertTuangouGoods(TuangouGoods g);
    //改团购商品
    @Update("update tuangou_goods set (goods_id=#{goods_id},miaosha_price=#{miaosha_price},start_date=#{start_date},end_date=#{end_date} where goods_id = #{goodsId}")
    int updateTuangouGoods(TuangouGoods g);
    //删团购商品
    @Delete("delete  from tuangou_goods where id = #{goodsId}")
    int deleteTuangouGoodsVoById(@Param("goodsId") long goodsId);

    @Update("update tuangou_goods set stock_count = #{stockCount} where goods_id = #{goodsId}")
    int resetStock1(TuangouGoods g);

}
