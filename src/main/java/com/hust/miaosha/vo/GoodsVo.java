package com.hust.miaosha.vo;

import com.hust.miaosha.domain.Goods;

import java.sql.Date;

/**
 * @program: miaosha1
 * @description: 秒杀商品的VO,集合秒杀商品和商品的集中属性于一体
 * @author: XuJY
 * @create: 2022-02-28 15:04
 **/
public class GoodsVo extends Goods {

    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;



    public Integer getStockCount() {
        return stockCount;
    }
    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public Double getMiaoshaPrice() {
        return miaoshaPrice;
    }

}
