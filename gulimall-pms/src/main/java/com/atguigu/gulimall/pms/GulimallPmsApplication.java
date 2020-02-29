package com.atguigu.gulimall.pms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@RefreshScope
// 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />  old version添加开启事务
@EnableTransactionManagement
@SpringBootApplication
@MapperScan(basePackages = "com.atguigu.gulimall.pms.dao")
public class GulimallPmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallPmsApplication.class, args);
    }

}
