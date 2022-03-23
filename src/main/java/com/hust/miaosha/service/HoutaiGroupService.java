package com.hust.miaosha.service;

import com.hust.miaosha.dao.GroupDao;
import com.hust.miaosha.domain.Group;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author shucheng
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2022/3/23
 */
@Service
@Slf4j
public class HoutaiGroupService {
    @Resource
    GroupDao groupDao;

    /**
     * 查询所有组
     * @return
     */
    public List<Group> queryAll(){
        return groupDao.query();
    }

    /**
     * id查询
     * @param id
     * @return
     */
    public Group queryById(Long id){
        return groupDao.getGroup(id);
    }
    /**
     * 删除组
     */
    public int delete(Long id){
        return groupDao.delete(id);
    }

    /**
     * 更新组
     * @param group
     * @return
     */
    public int update(Group group){
        return groupDao.update(group);
    }
}

