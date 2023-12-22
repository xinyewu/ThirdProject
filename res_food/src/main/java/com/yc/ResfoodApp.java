package com.yc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.yc.dao")
//@EnableSwagger2//启用这个框架专有注解解析器, 其他框架如果增加了新的标记，都要@EnableXXX
@EnableRedisHttpSession
@EnableDiscoveryClient//启用服务注册发现客户端
public class ResfoodApp {
    public static void main(String[] args) {
        SpringApplication.run(ResfoodApp.class, args);
    }
}
