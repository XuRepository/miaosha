package com.hust.miaosha.service;

import com.hust.miaosha.dao.MiaoshaUserDao;
import com.hust.miaosha.dao.UserDao;
import com.hust.miaosha.domain.MiaoshaUser;
import com.hust.miaosha.exception.GlobalException;
import com.hust.miaosha.redis.RedisService;
import com.hust.miaosha.redis.keyPrefix.MiaoshaUserKey;
import com.hust.miaosha.result.CodeMsg;
import com.hust.miaosha.util.MD5Util;
import com.hust.miaosha.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author shucheng
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2022/3/20
 */
@Service("houtaiUserService")
@Transactional(propagation = Propagation.REQUIRED)
public class HoutaiUserService {

    @Autowired
    private UserDao userDao;

    public static final String COOKIE_NAME_TOKEN = "token";

    @Resource
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;


    //对象级缓存
    public MiaoshaUser getById(long id) {

        //取缓存
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, "" + id, MiaoshaUser.class);
        if (user != null) {
            return user;
        }
        //取数据库
        user = miaoshaUserDao.getById(id);
        if (user != null) {
            // 取数据库后先存缓存，再返回
            redisService.set(MiaoshaUserKey.getById, "" + id, user);
        }
        return user;
    }


    //修改密码
    // http://blog.csdn.net/tTU1EvLDeLFq5btqiK/article/details/78693323
    public boolean updatePassword(String token, long id, String formPass) {
        //取user
        MiaoshaUser user = getById(id);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //更新数据库
        MiaoshaUser toBeUpdate = new MiaoshaUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass, user.getSalt()));
        miaoshaUserDao.update(toBeUpdate);

        //处理缓存
        redisService.delete(MiaoshaUserKey.getById, "" + id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(MiaoshaUserKey.token, token, user);
        return true;
    }


    //tu==添加cookie并且把token miaoshauser对应写入redis
    public void addCookie(HttpServletResponse response,String token,MiaoshaUser user){
        redisService.set(MiaoshaUserKey.token,token,user);

        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());//更新有效时间
        cookie.setPath("/");//路径为根目录
        response.addCookie(cookie);
    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {

        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //延长有效期
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    /**\
     * 时序图：插入
     * @param userVo
     * @return
     */
    public int insert(UserVo userVo){
        MiaoshaUser miaoshaUser = new MiaoshaUser();
        miaoshaUser.setId(userVo.getId());
        miaoshaUser.setNickname(userVo.getNickname());
        miaoshaUser.setPassword(MD5Util.inputPassToDbPass(userVo.getPassword(),"1a2b3c4d"));

        miaoshaUser.setSalt("1a2b3c4d");
        miaoshaUser.setLoginCount(0);
        miaoshaUser.setLastLoginDate(new Date());
        miaoshaUser.setRegisterDate(new Date());
        return miaoshaUserDao.insert(miaoshaUser);
    }

    //时序图方法
    public MiaoshaUser getById_1(long id){

        return miaoshaUserDao.getById(id);

    }
    //更新
    public int update(UserVo userVo){

        MiaoshaUser miaoshaUser = new MiaoshaUser();
        miaoshaUser.setId(userVo.getId());
        miaoshaUser.setNickname(userVo.getNickname());
        miaoshaUser.setPassword(MD5Util.inputPassToDbPass(userVo.getPassword(),"1a2b3c4d"));

        miaoshaUser.setSalt("1a2b3c4d");
        miaoshaUser.setLoginCount(0);
        miaoshaUser.setLastLoginDate(new Date());
        miaoshaUser.setRegisterDate(new Date());
        return miaoshaUserDao.update(miaoshaUser);

    }
//  删除用户
    public  int delete(long id){
        return miaoshaUserDao.deleteUser(id);
    }
    //查询所有用户
    public List<MiaoshaUser> query(){
        return miaoshaUserDao.queryUsers();
    }

}
