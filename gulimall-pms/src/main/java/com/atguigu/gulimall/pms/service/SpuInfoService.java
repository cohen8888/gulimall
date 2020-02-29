package com.atguigu.gulimall.pms.service;

import com.atguigu.gulimall.pms.vo.BaseAttrVO;
import com.atguigu.gulimall.pms.vo.SkuVO;
import com.atguigu.gulimall.pms.vo.SpuAllInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.pms.entity.SpuInfoEntity;
import com.atguigu.gulimall.commons.bean.PageVo;
import com.atguigu.gulimall.commons.bean.QueryCondition;

import java.util.List;


/**
 * spu信息
 *
 * @author cohen
 * @email cohen@qq.com
 * @date 2020-02-25 12:50:21
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageVo queryPage(QueryCondition params);

    PageVo queryPageByCatId(QueryCondition queryCondition, Long catId);

    void saveAllInfoVO(SpuAllInfoVO spuAllInfoVO);
    void saveSpuInfoImages(Long spuId, String[] spuImages);
    Long saveSpuBaseInfo(SpuAllInfoVO spuAllInfoVO);
    void saveSkuInfos(Long spuId, List<SkuVO> skus);

    void saveSpuBaseInfoAttrs(Long spuId, List<BaseAttrVO> baseAttrs);
}

