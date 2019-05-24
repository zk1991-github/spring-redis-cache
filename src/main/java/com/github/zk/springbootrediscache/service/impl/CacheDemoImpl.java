package com.github.zk.springbootrediscache.service.impl;

import com.github.zk.springbootrediscache.entity.User;
import com.github.zk.springbootrediscache.service.ICacheDemo;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User selectAllUser() {
        System.out.println("selectAllUser未使用缓存");
        String name = "张三";
        long time = System.currentTimeMillis();
        User user = new User(0 , name , time);
        return user;
    }
    /**
     * 存储在cache-temp缓存名
     * 使用默认key生成器
     * 使用默认cacheManager
     * @param id
     * @return
     */
    @Override
    @Cacheable(cacheNames = "cache-temp")
    public User selectUserById(int id) {
        System.out.println("selectUserById未使用缓存");
        String name = "张三" + id;
        long time = System.currentTimeMillis();
        User user = new User(id , name , time);
        return user;
    }

    @Override
    @Cacheable(cacheNames = "cache-temp" , key = "#id")
    public User selectUserById2(int id) {
        System.out.println("selectUserById2未使用缓存");
        String name = "张三" + id;
        long time = System.currentTimeMillis();
        User user = new User(id , name , time);
        return user;
    }

    @Override
    @Cacheable(cacheNames = "cache")
    public User selectUserByKeyword(int id, String name) {
        System.out.println("selectUserByKeyword未使用缓存");
        long time = System.currentTimeMillis();
        User user = new User(id , name , time);
        return user;
    }

    @Override
    @Cacheable(cacheNames = "cache" , key = "'[' + #name + ']'")
    public User selectUserByName(String name) {
        System.out.println("selectUserByName");
        int id = 1;
        long time = System.currentTimeMillis();
        User user = new User(id , name , time);
        return user;
    }

    @Override
    @CachePut(cacheNames = "cache-temp" , key = "#id")
    public User updateUserById(int id) {
        System.out.println("updatUserById");
        String name = "张三" + id;
        long time = System.currentTimeMillis();
        User user = new User(id , name , time);
        return user;
    }


    @Override
    @CacheEvict(cacheNames = "cache-temp" , key = "#id")
    public void delUserById(int id) {
        System.out.println("根据ID删除用户。。。");
    }
    @Override
    @CacheEvict(cacheNames = "cache-temp" , allEntries = true)
    public void delAllCacheTemp() {
        System.out.println("删除所有cache-temp缓存");
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "cache-temp" , key = "#id[0]"),
                    @CacheEvict(cacheNames = "cache-temp" , key = "#id[1]")
            }
    )
    public void delUser(int... id) {
        System.out.println("删除用户。。。");
    }
}
