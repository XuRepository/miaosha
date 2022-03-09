package com.hust.miaosha.dao;

import com.hust.miaosha.domain.Group;
import com.hust.miaosha.domain.MiaoshaGoods;
import com.hust.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: miaosha1
 * @description: 团购秒杀-时序图
 * @author: XuJY
 * @create: 2022-03-09 14:15
 **/
@Mapper
public interface GroupDao {

    public void createGroup(MiaoshaUser user);

    public void joinGroup(MiaoshaUser miaoshaUser, long groupId);

    public Group getGroup(MiaoshaUser miaoshaUser);

    public void groupMiaosha(MiaoshaUser miaoshaUser, Group group, MiaoshaGoods goods);



}
