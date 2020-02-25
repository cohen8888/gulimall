package com.atguigu.gulimall.pms.dao;

import com.atguigu.gulimall.pms.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author cohen
 * @email cohen@qq.com
 * @date 2020-02-25 12:50:21
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
