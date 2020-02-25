package com.atguigu.gulimall.ums.dao;

import com.atguigu.gulimall.ums.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author cohen
 * @email cohen@qq.com
 * @date 2020-02-25 15:46:14
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
