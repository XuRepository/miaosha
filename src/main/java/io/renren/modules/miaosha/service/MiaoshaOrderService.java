package io.renren.modules.miaosha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.miaosha.service.entity.MiaoshaOrderEntity;

import java.util.Map;

/**
 *
 *
 * @author miaosha
 * @email sunlightcs@gmail.com
 * @date 2022-03-27 19:10:12
 */
public interface MiaoshaOrderService extends IService<MiaoshaOrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

