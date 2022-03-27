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

import io.renren.modules.miaosha.service.entity.MiaoshaUserEntity;
import io.renren.modules.miaosha.service.MiaoshaUserService;
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
@RequestMapping("miaosha/miaoshauser")
public class MiaoshaUserController {
    @Autowired
    private MiaoshaUserService miaoshaUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("miaosha:miaoshauser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = miaoshaUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("miaosha:miaoshauser:info")
    public R info(@PathVariable("id") Long id){
		MiaoshaUserEntity miaoshaUser = miaoshaUserService.getById(id);

        return R.ok().put("miaoshaUser", miaoshaUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("miaosha:miaoshauser:save")
    public R save(@RequestBody MiaoshaUserEntity miaoshaUser){
		miaoshaUserService.save(miaoshaUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("miaosha:miaoshauser:update")
    public R update(@RequestBody MiaoshaUserEntity miaoshaUser){
		miaoshaUserService.updateById(miaoshaUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("miaosha:miaoshauser:delete")
    public R delete(@RequestBody Long[] ids){
		miaoshaUserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
