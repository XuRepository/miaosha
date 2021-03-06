package com.hust.miaosha.dao;

import com.hust.miaosha.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @program: miaosha1
 * @description:
 * @author: XuJY
 * @create: 2022-02-25 20:18
 **/
@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    public User getByID(@Param("id") int id);

    @Insert("insert into user(id,name) values(#{id},#{name})")
    public int insert(User user);


}
