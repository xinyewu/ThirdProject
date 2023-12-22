1.客户端负载均衡 
  a.将请求地址有固定的ip：端口的方式改为服务名访问 
  b.引入loadbalancer依赖 ：
     <!--@LoadBalanced 驱动·-->
     <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-loadbalancer</artifactId>
     </dependency>
  c.在客户端RestTemplate组件上加入： @LoadBalanced 注解;
     @LoadBalanced
     @Bean 
     public RestTemplate restTemplate() { return new RestTemplate();}
  d.配置不同的负载均衡策略：
  //方案一：全局配置负载均衡策略
  //@LoadBalancerClient(name = "resfood", configuration = {MyBalanceConfiguration.class})
  //方案二：对每个服务分别指定负载均衡策略
  @LoadBalancerClients(value = {
     @LoadBalancerClient(value = "resfood", configuration = MyBalanceConfiguration.class),
     @LoadBalancerClient(value = "resorder", configuration = MyBalanceConfiguration.class)}, defaultConfiguration = LoadBalancerClientConfiguration.class)

2.源码解读：

3.自定义负载均衡策略：
  a.写一个类实现ReactorServiceInstanceLoadBalancer接口，提供负载均衡算法
  b.通过@Configuration类托管上面的类
  @Bean
//@ConditionalOnMissingBean    spring容器中没有ReactorLoadBalancer这个bean,才能创建
public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(Environment environment, LoadBalancerClientFactory loadBalancerClientFactory) {
String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
return new OnlyOneLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class)); }
  c.全局配置或针对某个服务配置balancer

====================扩展新知识========================
1.原子类
  AtomicInteger.incrementAndGet()
  int i=0;
  i++;
  i=0;
  i=i+1;
2.线程安全的随机数类：与本地线程绑定的随机数生成器ThreadLocalRandom.current().nextInt(instance.size());
3.spring5中的响应式编程模式: Flux类+Mono类
  Flux类  Flux.defer(()->Flux.just(Collection.emptyList())
  Mono类
4.spring MVC中的WebFlux的用法
5.spring security
===============================================
设计目标：单一职责，接口隔离，依赖倒置，里氏替换原则，开闭原则，迪米特法则
构建型：
1.单例模式，DCL双重检查锁的单例
1.简单工厂->工厂方法模式
3.构建器模式
结构性：
1.代理模式jdk动态代理/cglib代理  mybatisplus或feign如何实现代理模式
行为型：
1.模板模式 JDBCTemplate(RowMapper),RedisTemplate
2.责任链模式 j2ee的Filter,springMVC的Interceptor,Sentinel的Slot,gateWay


  
    
    