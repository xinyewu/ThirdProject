package com.yc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@MapperScan("com.yc.dao")
@EnableRedisHttpSession
@EnableDiscoveryClient
public class ResuserApp {
    public static void main(String[] args) {
        SpringApplication.run(ResuserApp.class,args);
    }
}
