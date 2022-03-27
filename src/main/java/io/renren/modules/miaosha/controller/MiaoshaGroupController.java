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

import io.renren.modules.miaosha.service.entity.MiaoshaGroupEntity;
import io.renren.modules.miaosha.service.MiaoshaGroupService;
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
@RequestMapping("miaosha/miaoshagroup")
public class MiaoshaGroupController {
    @Autowired
    private MiaoshaGroupService miaoshaGroupService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("miaosha:miaoshagroup:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = miaoshaGroupService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{groupId}")
    @RequiresPermissions("miaosha:miaoshagroup:info")
    public R info(@PathVariable("groupId") Long groupId){
		MiaoshaGroupEntity miaoshaGroup = miaoshaGroupService.getById(groupId);

        return R.ok().put("miaoshaGroup", miaoshaGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("miaosha:miaoshagroup:save")
    public R save(@RequestBody MiaoshaGroupEntity miaoshaGroup){
		miaoshaGroupService.save(miaoshaGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("miaosha:miaoshagroup:update")
    public R update(@RequestBody MiaoshaGroupEntity miaoshaGroup){
		miaoshaGroupService.updateById(miaoshaGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("miaosha:miaoshagroup:delete")
    public R delete(@RequestBody Long[] groupIds){
		miaoshaGroupService.removeByIds(Arrays.asList(groupIds));

        return R.ok();
    }

}
