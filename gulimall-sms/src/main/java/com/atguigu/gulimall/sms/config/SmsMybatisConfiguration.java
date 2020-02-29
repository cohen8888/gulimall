package com.atguigu.gulimall.sms.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.atguigu.gulimall.sms.dao")
public class SmsMybatisConfiguration {

}
