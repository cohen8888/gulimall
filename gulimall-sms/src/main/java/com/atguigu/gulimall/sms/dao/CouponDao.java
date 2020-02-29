package com.atguigu.gulimall.sms.dao;

import com.atguigu.gulimall.sms.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author cohen
 * @email cohen@qq.com
 * @date 2020-02-29 19:28:13
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
