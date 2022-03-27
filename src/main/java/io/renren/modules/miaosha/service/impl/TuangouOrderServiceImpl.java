package io.renren.modules.miaosha.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.miaosha.dao.TuangouOrderDao;
import io.renren.modules.miaosha.service.entity.TuangouOrderEntity;
import io.renren.modules.miaosha.service.TuangouOrderService;


@Service("tuangouOrderService")
public class TuangouOrderServiceImpl extends ServiceImpl<TuangouOrderDao, TuangouOrderEntity> implements TuangouOrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TuangouOrderEntity> page = this.page(
                new Query<TuangouOrderEntity>().getPage(params),
                new QueryWrapper<TuangouOrderEntity>()
        );

        return new PageUtils(page);
    }

}
