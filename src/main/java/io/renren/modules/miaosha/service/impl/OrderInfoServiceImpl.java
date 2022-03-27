package io.renren.modules.miaosha.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.miaosha.dao.OrderInfoDao;
import io.renren.modules.miaosha.service.entity.OrderInfoEntity;
import io.renren.modules.miaosha.service.OrderInfoService;


@Service("orderInfoService")
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoDao, OrderInfoEntity> implements OrderInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderInfoEntity> page = this.page(
                new Query<OrderInfoEntity>().getPage(params),
                new QueryWrapper<OrderInfoEntity>()
        );

        return new PageUtils(page);
    }

}
