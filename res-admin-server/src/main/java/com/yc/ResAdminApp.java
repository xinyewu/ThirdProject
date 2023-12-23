package com.yc;

import com.yc.config.OpenFeignLogConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.yc.api"},defaultConfiguration = OpenFeignLogConfiguration.class)//openfeign扫描接口所在的包-》生成代理类
public class ResAdminApp {
    public static void main(String[] args) {
        SpringApplication.run(ResAdminApp.class,args);
    }
}
