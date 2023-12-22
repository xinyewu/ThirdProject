package com.yc;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableDiscoveryClient
@Data
@Slf4j
public class GatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }

    //全局过滤器另一个种写法: 核心：创建全局过滤器 GlobalFilter,并托管即可
    @Bean
    @Order(10000)
    public GlobalFilter hello() {
        return (exchange, chain) -> {
            log.info("hello");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("bye");
            }));
        };
    }
}