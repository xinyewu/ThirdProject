package com.yc.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@Slf4j
@RefreshScope //druid数据源的创建必须由用户自己创建而不能是自动配置的， why》因为自己配置的才可以加@RefreshScope
public class MyDruidDataSourceConfig {
    //将原来的springboot的自动IOC DruidDatasource方案改为 手工编程
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean  //IOC
    @Primary  //如果应用中有多个数据源DruidDataSource的话，优先使用这个代码IOC
    @RefreshScope
    public DataSource druid() {
        log.info("使用的编程式的数据源创建 DruidDataSource.");
        DruidDataSource ds = new DruidDataSource();
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(this.driverClassName);
        ds.setUrl(url);
        return ds;

    }

}
