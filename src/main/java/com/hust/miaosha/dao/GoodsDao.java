package com.hust.miaosha.dao;

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

    @Update("update tuangou_goods set stock_count = #{stockCount} where goods_id = #{goodsId}")
    int resetStock1(TuangouGoods g);
}
