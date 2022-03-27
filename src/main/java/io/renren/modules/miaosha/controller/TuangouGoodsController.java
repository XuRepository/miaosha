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

import io.renren.modules.miaosha.service.entity.TuangouGoodsEntity;
import io.renren.modules.miaosha.service.TuangouGoodsService;
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
@RequestMapping("miaosha/tuangougoods")
public class TuangouGoodsController {
    @Autowired
    private TuangouGoodsService tuangouGoodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("miaosha:tuangougoods:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tuangouGoodsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("miaosha:tuangougoods:info")
    public R info(@PathVariable("id") Long id){
		TuangouGoodsEntity tuangouGoods = tuangouGoodsService.getById(id);

        return R.ok().put("tuangouGoods", tuangouGoods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("miaosha:tuangougoods:save")
    public R save(@RequestBody TuangouGoodsEntity tuangouGoods){
		tuangouGoodsService.save(tuangouGoods);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("miaosha:tuangougoods:update")
    public R update(@RequestBody TuangouGoodsEntity tuangouGoods){
		tuangouGoodsService.updateById(tuangouGoods);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("miaosha:tuangougoods:delete")
    public R delete(@RequestBody Long[] ids){
		tuangouGoodsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
