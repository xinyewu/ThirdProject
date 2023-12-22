package com.yc.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
//方案一：全局配置负载均衡策略
//@LoadBalancerClient(name = "resfood", configuration = {MyBalanceConfiguration.class})
//对每个服务分别指定负载均衡策略
@LoadBalancerClients(
        value = {
                @LoadBalancerClient(value = "resfood", configuration = MyBalanceConfiguration.class),
                @LoadBalancerClient(value = "resorder", configuration = MyBalanceConfiguration.class)
        }, defaultConfiguration = LoadBalancerClientConfiguration.class
)
public class WebConfig {
    @LoadBalanced //负载均衡，resfood服务下有两个服务结点，那么一共请求到底会反问那个结点呢？    cloud的东西
    @Bean  //IOC 将restTemplate托管到spring
    public RestTemplate restTemplate() {
        return new RestTemplate(); //http://resfood/resfood/findById/
    }
}
