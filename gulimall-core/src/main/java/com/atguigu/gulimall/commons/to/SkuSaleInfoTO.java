package com.atguigu.gulimall.commons.to;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkuSaleInfoTO {

    private Long spuId;
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
