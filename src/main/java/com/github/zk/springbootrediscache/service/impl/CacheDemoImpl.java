package com.github.zk.springbootrediscache.service.impl;

import com.github.zk.springbootrediscache.entity.User;
import com.github.zk.springbootrediscache.service.ICacheDemo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author zk
 * @Date 2019/4/18 22:45
 */
@Service
public class CacheDemoImpl implements ICacheDemo {
    @Override
    @Cacheable(cacheNames = "cache-temp")
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

    @Override
    @CachePut(cacheNames = "cache-temp" , key = "'id=['+ #id +']'")
    public User updateUserById(int id) {
        System.out.println("updatUserById");
        User user = new User();
        user.setId(id);
        user.setName("李四");
        user.setTime(System.currentTimeMillis());
        return user;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "cache-temp" , key = "'id=[1]'"),
                    @CacheEvict(cacheNames = "cache-temp" , key = "'id=[2]'")
            }
    )
    public void deleteUser() {
        System.out.println("删除用户。。。");
    }
}
