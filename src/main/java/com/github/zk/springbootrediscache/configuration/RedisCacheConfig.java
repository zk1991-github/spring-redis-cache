package com.github.zk.springbootrediscache.configuration;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.*;

/**
 * @Author zk
 * @Date 2019/4/18 22:16
 */
@Configuration
@ConfigurationProperties(prefix = "spring.cache.redis")
public class RedisCacheConfig extends CachingConfigurerSupport {
    private Duration timeToLive = Duration.ZERO;

    public void setTimeToLive(Duration timeToLive) {
        this.timeToLive = timeToLive;
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (object,method,objects)->{
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(object.getClass().getSimpleName());
            stringBuffer.append(":");
            stringBuffer.append(method.getName());
            for (Object o : objects) {
                stringBuffer.append(":");
                stringBuffer.append(o.toString());
            }
            return stringBuffer.toString();
        };
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
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                //设置存活时间
//                .entryTtl(timeToLive)
                //设置key值序列化器
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                //设置value值序列化器
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer))
                //不存储null值
                .disableCachingNullValues();
        //定义缓存类型，cache-map/cache，如果需要增加新类型可通过add添加
        Set<String> cacheNames = new HashSet<String>(2);
        cacheNames.add("cache-temp");
        cacheNames.add("cache");

        //定义不同缓存类型的失效时间，key值与上面cacheNames中定义的需要一致，才会生效
        Map<String,RedisCacheConfiguration> configMap = new HashMap<String,RedisCacheConfiguration>(2);
        configMap.put("cache-temp",redisCacheConfiguration.entryTtl(timeToLive));
        configMap.put("cache",redisCacheConfiguration);

        //RedisCacheManager默认是无锁的，用于读写二进制值的RedisCacheWriter。
        // 无锁缓存可提高吞吐量。
        // 缺少锁定可能导致putIfAbsent和clean方法的非原子命令重叠，因为这些方法需要向Redis发送多条命令。
        // 锁对应程序通过设置显式锁定的key并检查是否存在该key来防止命令重叠，这会导致额外的请求和潜在的命令等待时间
        //构建RedisCache管理对象,采用有锁方式
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(RedisCacheWriter.lockingRedisCacheWriter(connectionFactory))
                //先初始化缓存名称，再加载缓存配置
                .initialCacheNames(cacheNames)
                //加载缓存配置
                .withInitialCacheConfigurations(configMap)
                //构建RedisCacheManage对象
                .build();
        return redisCacheManager;
    }
}
