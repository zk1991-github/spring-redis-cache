package com.github.zk.springbootrediscache.service.impl;

import com.github.zk.springbootrediscache.entity.User;
import com.github.zk.springbootrediscache.service.ICacheDemo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Author zk
 * @Date 2019/4/18 22:45
 */
@Service
public class CacheDemoImpl implements ICacheDemo {
    @Override
    @Cacheable(cacheNames = "selectUserById" , key = "'id=['+ #id +']'")
    public User selectUserById(int id) {
        System.out.println("未使用缓存");
        User user = new User();
        user.setId(id);
        user.setName("张三" + id);
        user.setTime(System.currentTimeMillis());
        return user;
    }
}
