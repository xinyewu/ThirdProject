package com.yc;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class MyTimeGatewayFilterFactory extends AbstractGatewayFilterFactory<MyTimeGatewayFilterFactory.MyTimeConfig> {
    public MyTimeGatewayFilterFactory() {
        super(MyTimeConfig.class);
    }

    @Override
    public GatewayFilter apply(MyTimeConfig config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                //在进入filter
                log.info("传入的参数:" + config.key + ":" + config.value);
                //记录开始时间
                long startTime = System.currentTimeMillis();

                return chain.filter(exchange).then(
                        //filter后期处理
                        Mono.fromRunnable(() -> {
                            long endTime = System.currentTimeMillis();
                            log.info("总时间:" + (endTime - startTime)+"毫秒");
                        })
                );
            }
        };
    }

    //在配置 中的属性的位置
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("key", "value");
    }

    //配置信息类
    @Data
    public static class MyTimeConfig {
        private String key;
        private String value;
    }
}