package com.atguigu.gulimall.pms.controller;

import java.util.Arrays;
import java.util.Map;


import com.atguigu.gulimall.commons.bean.PageVo;
import com.atguigu.gulimall.commons.bean.QueryCondition;
import com.atguigu.gulimall.commons.bean.Resp;
import com.atguigu.gulimall.pms.entity.AttrGroupEntity;
import com.atguigu.gulimall.pms.service.AttrGroupService;
import com.atguigu.gulimall.pms.vo.AttributeCarryGroupVO;
import com.atguigu.gulimall.pms.vo.AttributeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.pms.entity.AttrEntity;
import com.atguigu.gulimall.pms.service.AttrService;




/**
 * 商品属性
 *
 * @author cohen
 * @email cohen@qq.com
 * @date 2020-02-25 12:50:21
 */
@Api(tags = "商品属性 管理")
@RestController
@RequestMapping("pms/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @Autowired
    private AttrGroupService attrGroupService;
    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('pms:attr:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = attrService.queryPage(queryCondition);
        return Resp.ok(page);
    }

    @ApiOperation("获取商品基本属性信息")
    @GetMapping("/base/{catId}")
    public Resp<PageVo> getBaseAttrs(QueryCondition queryCondition, @PathVariable("catId") Long cateId){
        PageVo pageVo = attrService.queryPageCategoryBaseAttrs(queryCondition, cateId);
        return Resp.ok(pageVo);
    }
    @ApiOperation("获取商品销售属性信息")
    @GetMapping("/sale/{catId}")
    public Resp<PageVo> getSaleAttrs(QueryCondition queryCondition, @PathVariable("catId") Long cateId){
        PageVo pageVo = attrService.queryPageCategorySaleAttrs(queryCondition, cateId);
        return Resp.ok(pageVo);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{attrId}")
    @PreAuthorize("hasAuthority('pms:attr:info')")
    public Resp<AttributeCarryGroupVO> info(@PathVariable("attrId") Long attrId){

        AttributeCarryGroupVO result = new AttributeCarryGroupVO();
		AttrEntity attr = attrService.getById(attrId);
        BeanUtils.copyProperties(attr, result);

        AttrGroupEntity attrGroupEntity = attrGroupService.getGroupInfoByAttrId(attrId);
        result.setGroup(attrGroupEntity);

        return Resp.ok(result);
    }

    /**
     * 保存
     */
    @ApiOperation("保存属性")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:attr:save')")
    public Resp<Object> save(@RequestBody AttributeVO attr){
		attrService.saveAttributeAndRelation(attr);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('pms:attr:update')")
    public Resp<Object> update(@RequestBody AttrEntity attr){
		attrService.updateById(attr);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('pms:attr:delete')")
    public Resp<Object> delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return Resp.ok(null);
    }

}
