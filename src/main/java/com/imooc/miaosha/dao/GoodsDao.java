package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.MiaoshaGoods;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface GoodsDao {

    //LEFT JOIN 关键字会从左表 (Persons) 那里返回所有的行，即使在右表 (Orders) 中没有匹配的行。
    @Select("select  g.*, mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    List<GoodsVo> listGoodsVo();

    @Select("select  g.*, mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{id}")
    GoodsVo getGoodsVoById(@Param("id") long id) ;

    @Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId}")
    int reduceStock(MiaoshaGoods g);
}
