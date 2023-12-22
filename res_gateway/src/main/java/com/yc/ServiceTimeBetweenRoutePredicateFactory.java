package com.yc;

import lombok.Data;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class ServiceTimeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<ServiceTimeBetweenRoutePredicateFactory.CustomTimeBetweenConfig> {

    public ServiceTimeBetweenRoutePredicateFactory() {
        super(CustomTimeBetweenConfig.class);
    }

    /**
     * 业务逻辑判断
     *
     * @param config
     * @return
     */
    @Override
    public Predicate<ServerWebExchange> apply(CustomTimeBetweenConfig config) {
        //获取参数值
        LocalTime startTime = config.getStartTime();
        LocalTime endTime = config.getEndTime();
        //创建谓词对象
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                LocalTime now = LocalTime.now();
                //判断当前时间是否在在配置的时间范围类
                return now.isAfter(startTime) && now.isBefore(endTime);
            }
        };
    }

    /**
     * 用于接受yml中的配置CustomTimeBetween=上午6:00,下午12:00
     *
     * @return
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("startTime", "endTime");
    }

    /**
     * 配置类
     */
    @Data
    public static class CustomTimeBetweenConfig {
        private LocalTime startTime;
        private LocalTime endTime;
    }
}

