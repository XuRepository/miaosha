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

import io.renren.modules.miaosha.service.entity.MiaoshaOrderEntity;
import io.renren.modules.miaosha.service.MiaoshaOrderService;
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
@RequestMapping("miaosha/miaoshaorder")
public class MiaoshaOrderController {
    @Autowired
    private MiaoshaOrderService miaoshaOrderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("miaosha:miaoshaorder:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = miaoshaOrderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("miaosha:miaoshaorder:info")
    public R info(@PathVariable("id") Long id){
		MiaoshaOrderEntity miaoshaOrder = miaoshaOrderService.getById(id);

        return R.ok().put("miaoshaOrder", miaoshaOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("miaosha:miaoshaorder:save")
    public R save(@RequestBody MiaoshaOrderEntity miaoshaOrder){
		miaoshaOrderService.save(miaoshaOrder);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("miaosha:miaoshaorder:update")
    public R update(@RequestBody MiaoshaOrderEntity miaoshaOrder){
		miaoshaOrderService.updateById(miaoshaOrder);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("miaosha:miaoshaorder:delete")
    public R delete(@RequestBody Long[] ids){
		miaoshaOrderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
