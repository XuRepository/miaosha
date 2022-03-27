package io.renren.modules.miaosha.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.miaosha.dao.MiaoshaGoodsDao;
import io.renren.modules.miaosha.service.entity.MiaoshaGoodsEntity;
import io.renren.modules.miaosha.service.MiaoshaGoodsService;


@Service("miaoshaGoodsService")
public class MiaoshaGoodsServiceImpl extends ServiceImpl<MiaoshaGoodsDao, MiaoshaGoodsEntity> implements MiaoshaGoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MiaoshaGoodsEntity> page = this.page(
                new Query<MiaoshaGoodsEntity>().getPage(params),
                new QueryWrapper<MiaoshaGoodsEntity>()
        );

        return new PageUtils(page);
    }

}
