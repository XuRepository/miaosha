package com.hust.miaosha.dao;

import com.hust.miaosha.domain.MiaoshaGoods;
import com.hust.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: miaosha1
 * @description:
 * @author: XuJY
 * @create: 2022-03-09 15:05
 **/
@Mapper
public interface ShareDao {

    int shareGoods(MiaoshaUser user, MiaoshaGoods goods);

    boolean isShared();

    void shareMiaosha(MiaoshaUser user, MiaoshaGoods goods);
}

