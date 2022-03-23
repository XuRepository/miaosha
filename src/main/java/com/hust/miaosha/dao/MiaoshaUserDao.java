package com.hust.miaosha.dao;

import org.apache.ibatis.annotations.*;

import com.hust.miaosha.domain.MiaoshaUser;

import java.util.List;

@Mapper
public interface MiaoshaUserDao {

    @Select("select * from miaosha_user where id = #{id}")
    MiaoshaUser getById(@Param("id") long id);

    @Select("select group_id from miaosha_user where id = #{id}")
    Long getGroupIdById(@Param("id") long id);

    @Update("update miaosha_user set password = #{password} where id = #{id}")
    int update(MiaoshaUser user);

    @Update("update miaosha_user set group_id = #{groupId} where id = #{id}")
    int updateGroup(MiaoshaUser user);

    @Insert("insert into miaosha_user(id,nickname,password,salt,register_date) values(#{id},#{nickname},#{password},#{salt},#{registerDate})")
    int insert(MiaoshaUser user);

    /*
    时序图
     */
    @Update("update miaosha_user set (nickname,password,salt,register_date) values(#{nickname},#{password},#{salt},#{registerDate} where id = #{id})")
    int update1(MiaoshaUser user);
    /**
     * sc
     */
    //删除用户
    @Delete("delete from miaosha_user where id  = #{id}")
    int deleteUser(@Param("id") long id);
    //查询全部用户
    @Select("select * from miaosha_user")
    List<MiaoshaUser> queryUsers();
}
