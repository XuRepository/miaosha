package io.renren.modules.miaosha.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.miaosha.dao.MiaoshaOrderDao;
import io.renren.modules.miaosha.service.entity.MiaoshaOrderEntity;
import io.renren.modules.miaosha.service.MiaoshaOrderService;


@Service("miaoshaOrderService")
public class MiaoshaOrderServiceImpl extends ServiceImpl<MiaoshaOrderDao, MiaoshaOrderEntity> implements MiaoshaOrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MiaoshaOrderEntity> page = this.page(
                new Query<MiaoshaOrderEntity>().getPage(params),
                new QueryWrapper<MiaoshaOrderEntity>()
        );

        return new PageUtils(page);
    }

}
