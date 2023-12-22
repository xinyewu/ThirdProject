package com.yc;


import com.yc.config.OpenFeignLogConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@SpringBootApplication
@EnableDiscoveryClient//服务注册发现
@EnableRedisHttpSession //session + redis
@MapperScan("com.yc.dao")
@EnableFeignClients(basePackages = {"com.yc.api"},defaultConfiguration = OpenFeignLogConfiguration.class)//openfeign扫描接口所在的包-》生成代理类
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(
//        prePostEnabled = true,
//        securedEnabled = true,
//        jsr250Enabled = true)
public class ResorderApp {
    public static void main(String[] args) {
        SpringApplication.run(ResorderApp.class,args);
    }
}
