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

import io.renren.modules.miaosha.service.entity.TuangouOrderEntity;
import io.renren.modules.miaosha.service.TuangouOrderService;
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
@RequestMapping("miaosha/tuangouorder")
public class TuangouOrderController {
    @Autowired
    private TuangouOrderService tuangouOrderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("miaosha:tuangouorder:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tuangouOrderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("miaosha:tuangouorder:info")
    public R info(@PathVariable("id") Long id){
		TuangouOrderEntity tuangouOrder = tuangouOrderService.getById(id);

        return R.ok().put("tuangouOrder", tuangouOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("miaosha:tuangouorder:save")
    public R save(@RequestBody TuangouOrderEntity tuangouOrder){
		tuangouOrderService.save(tuangouOrder);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("miaosha:tuangouorder:update")
    public R update(@RequestBody TuangouOrderEntity tuangouOrder){
		tuangouOrderService.updateById(tuangouOrder);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("miaosha:tuangouorder:delete")
    public R delete(@RequestBody Long[] ids){
		tuangouOrderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
