package com.github.zk.springbootrediscache.service.impl;

import com.github.zk.springbootrediscache.entity.User;
import com.github.zk.springbootrediscache.service.ICacheDemo2;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author zk
 * @date 2019/5/21 9:58
 */
@Service
@CacheConfig(cacheNames = "cache-temp")
public class CacheDemoImpl2 implements ICacheDemo2 {
    @Override
    @Cacheable(key = "#id")
    public User selectUserById(int id) {
        System.out.println("selectUserById未使用缓存");
        String name = "张三" + id;
        long time = System.currentTimeMillis();
        User user = new User(id , name , time);
        return user;
    }

    @Override
    @Cacheable(key = "#id")
    public User selectUserById2(int id) {
        System.out.println("selectUserById2未使用缓存");
        String name = "张三" + id;
        long time = System.currentTimeMillis();
        User user = new User(id , name , time);
        return user;
    }

}
