package com.atguigu.gulimall.pms.vo;

import com.atguigu.gulimall.pms.entity.SpuImagesEntity;
import com.atguigu.gulimall.pms.entity.SpuInfoEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
/**
 * Spu的全部信息[spu基本信息、spu的基本属性， 所有sku的基本信息，所有sku的促销信息]
 *
 */
@Data
public class SpuAllInfoVO extends SpuInfoEntity {

    private List<BaseAttrVO> baseAttrs;
    private String[] spuImages;
    private List<SkuVO> skus;

}






