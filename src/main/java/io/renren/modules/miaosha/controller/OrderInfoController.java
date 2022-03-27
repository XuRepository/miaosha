package io.renren.modules.miaosha.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.miaosha.service.entity.OrderInfoEntity;
import io.renren.modules.miaosha.service.OrderInfoService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 *
 *
 * @author miaosha
 * @email sunlightcs@gmail.com
 * @date 2022-03-27 19:10:12
 */
@RestController
@RequestMapping("miaosha/orderinfo")
public class OrderInfoController {
    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("miaosha:orderinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("miaosha:orderinfo:info")
    public R info(@PathVariable("id") Long id){
		OrderInfoEntity orderInfo = orderInfoService.getById(id);

        return R.ok().put("orderInfo", orderInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("miaosha:orderinfo:save")
    public R save(@RequestBody OrderInfoEntity orderInfo){
		orderInfoService.save(orderInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("miaosha:orderinfo:update")
    public R update(@RequestBody OrderInfoEntity orderInfo){
		orderInfoService.updateById(orderInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("miaosha:orderinfo:delete")
    public R delete(@RequestBody Long[] ids){
		orderInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
