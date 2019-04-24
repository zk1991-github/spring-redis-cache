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
    @Cacheable(cacheNames = "cache-temp" , key = "'id=['+ #id +']'")
    public User selectUserById(int id) {
        System.out.println("selectUserById");
        User user = new User();
        user.setId(id);
        user.setName("张三" + id);
        user.setTime(System.currentTimeMillis());
        return user;
    }

    @Override
    @Cacheable(cacheNames = "cache" , key = "'[' + #name + ']'")
    public User selectUserByName(String name) {
        System.out.println("selectUserByName");
        User user = new User();
        user.setId(1);
        user.setName(name);
        user.setTime(System.currentTimeMillis());
        return user;
    }
}
