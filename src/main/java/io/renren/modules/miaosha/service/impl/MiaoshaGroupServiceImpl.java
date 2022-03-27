package io.renren.modules.miaosha.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.miaosha.dao.MiaoshaGroupDao;
import io.renren.modules.miaosha.service.entity.MiaoshaGroupEntity;
import io.renren.modules.miaosha.service.MiaoshaGroupService;


@Service("miaoshaGroupService")
public class MiaoshaGroupServiceImpl extends ServiceImpl<MiaoshaGroupDao, MiaoshaGroupEntity> implements MiaoshaGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MiaoshaGroupEntity> page = this.page(
                new Query<MiaoshaGroupEntity>().getPage(params),
                new QueryWrapper<MiaoshaGroupEntity>()
        );

        return new PageUtils(page);
    }

}
