package com.atguigu.gulimall.ums.config;


import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


//Swagger2的核心配置
@EnableSwagger2
@Configuration
public class UmsSwagger2Config {

    @Bean
    public Docket userApis(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("我的系统的名称")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.regex("/ums.*"))
                .build()
                .apiInfo(apiInfo())
                .enable(true);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("商品平台接口文档")                      //标题
                .description("描述描述描述描述描述描述")        //描述
                .termsOfServiceUrl("http://wwww.baidu.com")     //服务地址
                .version("1.0.0")                                 //版本号
                .build();
    }


}
