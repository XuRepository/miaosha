package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.UserDao;
import com.imooc.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: miaosha1
 * @description:
 * @author: XuJY
 * @create: 2022-02-25 20:23
 **/
@Service
public class UserService implements UserDao {

    @Autowired
    UserDao userDao;

    @Override
    public User getByID(int id) {
        return userDao.getByID(id);
    }

    @Override
    public int insert(User user) {
        return userDao.insert(user);
    }
}
