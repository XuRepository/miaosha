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

import io.renren.modules.miaosha.service.entity.GoodsEntity;
import io.renren.modules.miaosha.service.GoodsService;
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
@RequestMapping("miaosha/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("miaosha:goods:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = goodsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("miaosha:goods:info")
    public R info(@PathVariable("id") Long id){
		GoodsEntity goods = goodsService.getById(id);

        return R.ok().put("goods", goods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("miaosha:goods:save")
    public R save(@RequestBody GoodsEntity goods){
		goodsService.save(goods);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("miaosha:goods:update")
    public R update(@RequestBody GoodsEntity goods){
		goodsService.updateById(goods);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("miaosha:goods:delete")
    public R delete(@RequestBody Long[] ids){
		goodsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
