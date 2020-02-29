package com.atguigu.gulimall.pms.service.impl;

import com.atguigu.gulimall.pms.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.pms.vo.AttributeVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.commons.bean.PageVo;
import com.atguigu.gulimall.commons.bean.Query;
import com.atguigu.gulimall.commons.bean.QueryCondition;

import com.atguigu.gulimall.pms.dao.AttrDao;
import com.atguigu.gulimall.pms.entity.AttrEntity;
import com.atguigu.gulimall.pms.service.AttrService;
import org.springframework.transaction.annotation.Transactional;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public PageVo queryPageCategoryBaseAttrs(QueryCondition queryCondition, Long cateId) {
        //构建分页条件
        IPage<AttrEntity> page = new Query<AttrEntity>().getPage(queryCondition);

        //构建查询条件
        QueryWrapper<AttrEntity> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("catelog_id", cateId).eq("attr_type", 1);
        IPage<AttrEntity> iPageData = this.page(page, objectQueryWrapper);

        PageVo data = new PageVo(iPageData);

        return data;
    }

    @Override
    public PageVo queryPageCategorySaleAttrs(QueryCondition queryCondition, Long cateId) {
        //构建分页条件
        IPage<AttrEntity> page = new Query<AttrEntity>().getPage(queryCondition);

        //构建查询条件
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("catelog_id", cateId).eq("attr_type", 0);
        IPage<AttrEntity> iPageData = this.page(page, wrapper);

        PageVo data = new PageVo(iPageData);
        return data;
    }

    @Transactional
    @Override
    public void saveAttributeAndRelation(AttributeVO attr) {
        //先把属性
        AttrEntity attrEntity = new AttrEntity();
        //把AttributeVO数据拷贝到AttrEntity中
        BeanUtils.copyProperties(attr, attrEntity);

        this.save(attrEntity);
        //创建关联关系
        Long attrId = attrEntity.getAttrId();

        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrId(attrId);
        attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
        attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
        //int i = 0/0;
    }

}