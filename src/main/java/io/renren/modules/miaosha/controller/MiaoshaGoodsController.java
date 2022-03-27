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

import io.renren.modules.miaosha.service.entity.MiaoshaGoodsEntity;
import io.renren.modules.miaosha.service.MiaoshaGoodsService;
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
@RequestMapping("miaosha/miaoshagoods")
public class MiaoshaGoodsController {
    @Autowired
    private MiaoshaGoodsService miaoshaGoodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("miaosha:miaoshagoods:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = miaoshaGoodsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("miaosha:miaoshagoods:info")
    public R info(@PathVariable("id") Long id){
		MiaoshaGoodsEntity miaoshaGoods = miaoshaGoodsService.getById(id);

        return R.ok().put("miaoshaGoods", miaoshaGoods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("miaosha:miaoshagoods:save")
    public R save(@RequestBody MiaoshaGoodsEntity miaoshaGoods){
		miaoshaGoodsService.save(miaoshaGoods);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("miaosha:miaoshagoods:update")
    public R update(@RequestBody MiaoshaGoodsEntity miaoshaGoods){
		miaoshaGoodsService.updateById(miaoshaGoods);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("miaosha:miaoshagoods:delete")
    public R delete(@RequestBody Long[] ids){
		miaoshaGoodsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
