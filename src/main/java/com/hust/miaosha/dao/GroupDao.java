package com.hust.miaosha.dao;

import com.hust.miaosha.domain.Group;
import com.hust.miaosha.domain.MiaoshaGoods;
import com.hust.miaosha.domain.MiaoshaUser;
import com.hust.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: miaosha1
 * @description: 团购秒杀-时序图
 * @author: XuJY
 * @create: 2022-03-09 14:15
 **/
@Mapper
public interface GroupDao {

//    @Insert("insert into miaosha_user(id,nickname,password,salt,register_date) values(#{id},#{nickname},#{password},#{salt},#{registerDate})")

    @Insert("insert into miaosha_group(group_id,head_count,target_count) values(#{leaderId},#{headCount},10)")
    void createGroup(@Param("leaderId") long leaderId, @Param("headCount") int headCount);


    @Select("select * from miaosha_group where group_id =#{groupId}")
    Group getGroup(@Param("groupId") Long groupId);

    void groupMiaosha(MiaoshaUser miaoshaUser, Group group, MiaoshaGoods goods);

    @Update("update miaosha_group set head_count = #{headCount} where group_id = #{groupId}")
    void updateHeadCount(Group group);

/**
 * sc
 */
//查询全部分组
@Select("select * from miaosha_group ")
List<Group> query();
    //   删除特定分组
    @Delete("delete from miaosha_group where group_id =#{groupId}")
    int  delete(@Param("groupId") Long groupId);
    //update
    @Update("update miaosha_group set group_id = #{group_id },head_count= #{head_count },target_count= #{target_count }")
    int update(Group group);


}

