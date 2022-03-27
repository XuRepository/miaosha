package io.renren.modules.miaosha.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.miaosha.dao.TuangouGoodsDao;
import io.renren.modules.miaosha.service.entity.TuangouGoodsEntity;
import io.renren.modules.miaosha.service.TuangouGoodsService;


@Service("tuangouGoodsService")
public class TuangouGoodsServiceImpl extends ServiceImpl<TuangouGoodsDao, TuangouGoodsEntity> implements TuangouGoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TuangouGoodsEntity> page = this.page(
                new Query<TuangouGoodsEntity>().getPage(params),
                new QueryWrapper<TuangouGoodsEntity>()
        );

        return new PageUtils(page);
    }

}
