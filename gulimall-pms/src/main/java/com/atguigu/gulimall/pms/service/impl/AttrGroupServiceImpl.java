package com.atguigu.gulimall.pms.service.impl;

import com.atguigu.gulimall.pms.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.pms.dao.AttrDao;
import com.atguigu.gulimall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.pms.entity.AttrEntity;
import com.atguigu.gulimall.pms.vo.AttrGroupWithAttrVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.commons.bean.PageVo;
import com.atguigu.gulimall.commons.bean.Query;
import com.atguigu.gulimall.commons.bean.QueryCondition;

import com.atguigu.gulimall.pms.dao.AttrGroupDao;
import com.atguigu.gulimall.pms.entity.AttrGroupEntity;
import com.atguigu.gulimall.pms.service.AttrGroupService;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrAttrgroupRelationDao attrgroupRelationDao;

    @Autowired
    private AttrDao attrDao;


    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public PageVo queryPageGroupsByCateId(QueryCondition queryCondition, Long cateId) {
        //获取分页条件
        IPage<AttrGroupEntity> page =  new Query<AttrGroupEntity>().getPage(queryCondition);
        //获取查询条件
        QueryWrapper<AttrGroupEntity> attrGroupEntityQueryWrapper = new QueryWrapper<>();
        attrGroupEntityQueryWrapper.eq("catelog_id", cateId);
        //
        IPage<AttrGroupEntity> data = this.page(page, attrGroupEntityQueryWrapper);

        List<AttrGroupEntity> records = data.getRecords();

        List<AttrGroupWithAttrVO> groupWithAttrVOS = new ArrayList<AttrGroupWithAttrVO>(records.size());
        for (AttrGroupEntity record: records) {
            AttrGroupWithAttrVO vo = new AttrGroupWithAttrVO();
            BeanUtils.copyProperties(record, vo);
            Long groupId = record.getAttrGroupId();
            //查询分组的所有属性
            List<AttrAttrgroupRelationEntity> relationEntities = attrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", groupId));
            if (relationEntities != null && relationEntities.size() >0){
                List<Long> attrIds = new ArrayList<>();
                for (AttrAttrgroupRelationEntity entity : relationEntities) {
                    attrIds.add(entity.getAttrId());
                }
                List<AttrEntity> attrEntities = attrDao.selectList(new QueryWrapper<AttrEntity>().in("attr_id", attrIds));
                vo.setAttrEntities(attrEntities);
            }
            groupWithAttrVOS.add(vo);
        }
        PageVo vo = new PageVo(groupWithAttrVOS, data.getTotal(), data.getSize(), data.getCurrent());
        return vo;
    }

    @Override
    public AttrGroupWithAttrVO handlerAttrGroupRelationInfo(Long attrGroupId) {
        //创建一个属性分组和属性关系的值对象
        AttrGroupWithAttrVO attrGroupWithAttrVo = new AttrGroupWithAttrVO();

        //获取当前分组信息
        AttrGroupEntity attrGroupEntity = this.getById(attrGroupId);
        BeanUtils.copyProperties(attrGroupEntity, attrGroupWithAttrVo);

        //获取当前分组关联的所有属性
        QueryWrapper<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntityQueryWrapper = new QueryWrapper<>();
        attrAttrgroupRelationEntityQueryWrapper.eq("attr_group_id", attrGroupId);
        List<AttrAttrgroupRelationEntity> relationEntityList = attrgroupRelationDao.selectList(attrAttrgroupRelationEntityQueryWrapper);
        attrGroupWithAttrVo.setRelations(relationEntityList);

        //获取当前分组所有的属性
        List<Long>  attrIds = new ArrayList<>();
        relationEntityList.forEach(item -> {
            Long attrId = item.getAttrId();
            attrIds.add(attrId);
        });

        List<AttrEntity> attrs = attrIds.size() > 0 ? attrDao.selectList(new QueryWrapper<AttrEntity>().in("attr_id", attrIds))
                : new ArrayList<>();
        attrGroupWithAttrVo.setAttrEntities(attrs);

        return attrGroupWithAttrVo;
    }

    @Override
    public AttrGroupEntity getGroupInfoByAttrId(Long attrId) {

        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity =
                attrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>()
                        .eq("attr_id", attrId));
        if (attrAttrgroupRelationEntity == null)
            return null;
        Long attrGroupId = attrAttrgroupRelationEntity.getAttrGroupId();

        AttrGroupEntity attrGroupEntity = getById(attrGroupId);
        return attrGroupEntity;
    }


}