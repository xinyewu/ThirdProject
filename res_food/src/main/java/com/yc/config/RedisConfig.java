package com.yc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {
    /*
    键：值（String,List,Set,Hash,sortSet
    序列化：对象implements Serializable ->字节数组
     */
    @Bean
    public RedisTemplate<String,Object>redisTemplate(RedisConnectionFactory factory){
        //参考spring的RedisAutoConfiguration进行修改
        RedisTemplate<String,Object>template=new RedisTemplate();
        template.setConnectionFactory(factory);
        //序列化器

        //设置key的序列化方式  key->对象  String->json
        template.setKeySerializer(RedisSerializer.string());
        //设置value的序列化方式
        template.setValueSerializer(RedisSerializer.json());//json类型的字符串
        //设置hash的key的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());
        //设置hash的value的序列化方式
        template.setHashValueSerializer(RedisSerializer.json());

        template.afterPropertiesSet();
        return template;
    }
}
