package com.yc.web.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/**
 * 请求拦截器，统一加入 origin请求头信息
 */
@Component
public class CustomerRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Origin", "resorder");
        requestTemplate.header("Rere", "resorder1");//成功
        System.out.println("+++++++++++++++++++++++");
    }
}