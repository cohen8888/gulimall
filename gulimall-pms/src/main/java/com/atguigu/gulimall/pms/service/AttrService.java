package com.atguigu.gulimall.pms.service;

import com.atguigu.gulimall.pms.vo.AttributeVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.pms.entity.AttrEntity;
import com.atguigu.gulimall.commons.bean.PageVo;
import com.atguigu.gulimall.commons.bean.QueryCondition;


/**
 * 商品属性
 *
 * @author cohen
 * @email cohen@qq.com
 * @date 2020-02-25 12:50:21
 */
public interface AttrService extends IService<AttrEntity> {

    PageVo queryPage(QueryCondition params);

    PageVo queryPageCategoryBaseAttrs(QueryCondition queryCondition, Long cateId);

    PageVo queryPageCategorySaleAttrs(QueryCondition queryCondition, Long cateId);

    void saveAttributeAndRelation(AttributeVO attr);
}

