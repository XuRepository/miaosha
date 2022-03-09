package com.hust.miaosha.service;

import com.hust.miaosha.dao.GroupDao;
import com.hust.miaosha.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    MiaoshaService miaoshaService;

    public void createGroup(MiaoshaUser user) {
        groupDao.createGroup(user);

    }

    public void joinGroup(MiaoshaUser miaoshaUser, long groupId) {
        groupDao.joinGroup( miaoshaUser, groupId);
    }

    public Group getGroup(MiaoshaUser miaoshaUser) {
        return groupDao.getGroup(miaoshaUser);
    }

    public void groupMiaosha(MiaoshaUser user, Group group, MiaoshaGoods goods) {
        groupDao.groupMiaosha(user,group,goods);
    }
}
