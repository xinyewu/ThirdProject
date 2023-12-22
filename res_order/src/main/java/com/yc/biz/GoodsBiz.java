package com.yc.biz;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

@Service
public class GoodsBiz {
    @SentinelResource("goodsInfo")//将此方法定义为sentinel管理的资源
    public void goodsInfo(){
        System.out.println("商品信息");
    }
}
