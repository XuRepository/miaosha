package io.renren.modules.miaosha.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.miaosha.dao.MiaoshaUserDao;
import io.renren.modules.miaosha.service.entity.MiaoshaUserEntity;
import io.renren.modules.miaosha.service.MiaoshaUserService;


@Service("miaoshaUserService")
public class MiaoshaUserServiceImpl extends ServiceImpl<MiaoshaUserDao, MiaoshaUserEntity> implements MiaoshaUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MiaoshaUserEntity> page = this.page(
                new Query<MiaoshaUserEntity>().getPage(params),
                new QueryWrapper<MiaoshaUserEntity>()
        );

        return new PageUtils(page);
    }

}
