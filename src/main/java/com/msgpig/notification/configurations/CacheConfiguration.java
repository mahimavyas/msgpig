package com.msgpig.notification.configurations;

import org.springframework.cache.annotation.AnnotationCacheOperationSource;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching(mode = AdviceMode.PROXY, proxyTargetClass = false)
public class CacheConfiguration {
    // private @Value("${redis.host.name}") String redisHostName;
    // private @Value("${redis.port}") int redisPort;
    // private @Value("${redis.cache.ttl}") Long ttl;
    // private @Value("${redis.cache.dtl}") Long dtl;
    // @Value("${redis.db.no}")
    // private Integer redisDb;

    // @Bean
    // public JedisConnectionFactory lruJedisConnectionFactory() {
    // JedisConnectionFactory factory = new JedisConnectionFactory();
    // factory.setHostName(redisHostName);
    // factory.setPort(redisPort);
    // factory.setUsePool(true);
    // factory.setDatabase(redisDb);
    // return factory;
    // }

    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public JacksonJsonRedisSerializer<Object> jsonRedisSerializer() {
        return new JacksonJsonRedisSerializer<Object>(Object.class);
    }

    @Bean
    public JdkSerializationRedisSerializer jdkSerializationRedisSerializer() {
        return new JdkSerializationRedisSerializer();
    }

    @Bean
    AnnotationCacheOperationSource annotationCacheOperationSource() {
        return new AnnotationCacheOperationSource();
    }

    // @Bean
    // public RedisTemplate<Object, Object> redisTemplate() {
    // RedisTemplate<Object, Object> redisTemplate = new CacheTemplate<Object, Object>();
    // redisTemplate.setConnectionFactory(lruJedisConnectionFactory());
    // return redisTemplate;
    // }
    //
    // @Bean(name = "nileCacheManager")
    // @Override
    // public CacheManager cacheManager() {
    // final RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
    // cacheManager.setDefaultExpiration(ttl);
    // return (CacheManager) cacheManager;
    // }
    //
    // @Bean(name = "hourlyCacheManager")
    // public CacheManager hourlyCacheManager() {
    // final RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
    // cacheManager.setDefaultExpiration(3600);
    // return (CacheManager) cacheManager;
    // }
    //
    // @Bean(name = "dailyCacheManager")
    // public CacheManager DailyCacheManager() {
    // RedisTemplate<Object, Object> redisTemp = redisTemplate();
    // redisTemp.setKeySerializer(new StringRedisSerializer());
    // final RedisCacheManager cacheManager = new RedisCacheManager(redisTemp);
    // cacheManager.setDefaultExpiration(dtl);
    // return (CacheManager) cacheManager;
    // }
    //
    // @Bean
    // @Override
    // public KeyGenerator keyGenerator() {
    // return new SimpleKeyGenerator();
    // }

    // @Bean
    // @Override
    // public CacheResolver cacheResolver() {
    // return new SimpleCacheResolver(cacheManager());
    // }
    //
    // @Bean
    // @Override
    // public CacheErrorHandler errorHandler() {
    // return new SimpleCacheErrorHandler();
    // }
}
