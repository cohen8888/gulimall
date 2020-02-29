package com.atguigu.gulimall.pms.feign;


import com.atguigu.gulimall.commons.bean.Resp;
import com.atguigu.gulimall.commons.to.SkuSaleInfoTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("gulimall-sms")
public interface SmsSkuSaleInfoServiceFeign {

    @PostMapping("/sms/skubounds/saleinfo/save")
    public Resp<Object> saveSkuSaleInfos(@RequestBody SkuSaleInfoTO to);
}
