# springboot-xms-cloud
1. 流控回调
   异常回调

   -> 将回调处理整合,  springboot统一异常处理

2.  动态规则扩展:   -> 推模式(生产环境下)
    nacos的配置中心保存这些规则 .

================================
1. 职责链设计模式.   请求-> 经过slot.
2. SPI机制:
4. 限流算法:
   令牌桶算法,  漏桶算法.
5. transport:  http客户端
   spimple-http
