一般使用步骤:
准备步骤：先开发要暴露的API的相应的服务
1.创建api服务. 在此服务中加入要公开  API接口
 <dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-openfeign</artifactId>
 </dependency>
2. 在这个api服务中开发  接口:
   @FeignClient("resfood")
   public interface ResfoodApi {
     @RequestMapping( value="resfood/detailCountAdd" , method=RequestMethod.GET)
        public Map<String, Object> detailCountAdd(Integer fid);
   }
3. 调用端开发:
   <dependency>
      <artifactId>res-api</artifactId>
      <groupId>org.example</groupId>
      <version>1.0-SNAPSHOT</version>
   </dependency>
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-openfeign</artifactId>
   </dependency>
   b)开启openfeign的客户端     
       @EnableFeignClients(basePackages= {"com.yc.api"})
   c)注入api接口到controller或业务层.
       @Autowired
       private ResfoodApi resfoodApi;
   
spring cloud openfeign 底层是
