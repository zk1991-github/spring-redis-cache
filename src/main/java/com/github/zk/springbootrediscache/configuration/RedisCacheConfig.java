package com.github.zk.springbootrediscache.configuration;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @Author zk
 * @Date 2019/4/18 22:16
 */
@Component
@Configuration
@ConfigurationProperties(prefix = "spring.cache.redis")
public class RedisCacheConfig {
    private Duration timeToLive = Duration.ZERO;

    public void setTimeToLive(Duration timeToLive) {
        this.timeToLive = timeToLive;
    }

    @Bean
    @Primary//当有多个管理器的时候，必须使用该注解在一个管理器上注释：表示该管理器为默认的管理器
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();

        //初始化FastJson序列化器
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        /**
         * 解决查询缓存转换异常问题
          */
        //初始化配置
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        //设置序列化特征值，写入对象完整类名（@type）
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteClassName);
        fastJsonRedisSerializer.setFastJsonConfig(fastJsonConfig);
        //包授权
        ParserConfig.getGlobalInstance().addAccept("com.github.zk.springbootrediscache.entity.");

        //配置序列化，解决存储至Redis的乱码问题
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig() //初始化RedisCache配置
                .entryTtl(timeToLive) //设置存活时间
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)) //设置key值序列化器
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer)) //设置value值序列化器
                .disableCachingNullValues(); //不存储null值

        //构建RedisCache管理对象
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
        return redisCacheManager;
    }
}
