package com.atguigu.gulimall.oms.dao;

import com.atguigu.gulimall.oms.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author cohen
 * @email cohen@qq.com
 * @date 2020-02-25 16:42:21
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
