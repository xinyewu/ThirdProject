package com.yc.api.configuration;

import feign.Contract;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * feign客户端的各种组件：contract ,encode,decode,client,logger....
 */
public class ResorderApiConfiguration {
    //encode decode contract client....
//    @Bean
//    public Contract feignContract() {
//        return new feign.Contract.Default();
//    }
//
//    @Bean
//    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
//        return new BasicAuthRequestInterceptor("user", "password");
//    }
}
