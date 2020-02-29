package com.atguigu.gulimall.pms.vo;

import lombok.Data;

@Data
public class BaseAttrVO{
    private Long attrId;
    private String attrName;
    private String[] valueSelected;
}
