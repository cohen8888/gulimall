package com.atguigu.gulimall.pms.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuVO{
    //--------------------------sku基本信息
    private String skuName;         //sku的名字
    private String skuDesc;         //sku描述
    private String skuTitle;        //sku标题
    private String skuSubtitle;     //sku副标题
    private BigDecimal weight;      //重量
    private BigDecimal price;       //价格
    private String[] images;        //图片

    //--------------------------sku的销售属性
    private List<SaleAttrVO> saleAttrs;

    //--------------------------积分设置信息
    private BigDecimal growBounds;
    private BigDecimal buyBounds;
    //优惠生效情况[1111]四个状态位
    //0-无优惠，成长积分是否赠送；
    //1-无优惠，购物积分是否赠送；
    //2-有优惠，成长积分是否赠送；
    //3-有优惠，购物积分是否赠送[状态位0：不赠送，1：赠送]
    private Integer[] work;

    //--------------------------阶梯价格信息
    private Integer fullCount;
    private BigDecimal discount;
    private Integer ladderAddOther;

    //--------------------------满减信息
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private Integer fullAddOther;


}