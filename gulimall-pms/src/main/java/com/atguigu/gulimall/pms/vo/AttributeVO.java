package com.atguigu.gulimall.pms.vo;

import com.atguigu.gulimall.pms.entity.AttrEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class AttributeVO extends AttrEntity {
    private Long attrGroupId;
}
