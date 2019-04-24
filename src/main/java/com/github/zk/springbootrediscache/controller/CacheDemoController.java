package com.github.zk.springbootrediscache.controller;

import com.github.zk.springbootrediscache.entity.User;
import com.github.zk.springbootrediscache.service.ICacheDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zk
 * @Date 2019/4/18 22:51
 */
@RestController
@RequestMapping("/demo")
public class CacheDemoController {
    @Autowired
    private ICacheDemo iCacheDemo;
    @RequestMapping("/selectUserById")
    public User selectUserById(@RequestParam int id) {
        User user = iCacheDemo.selectUserById(id);
        return user;
    }

    @RequestMapping("/selectUserByName")
    public User selectUserByName(@RequestParam String name) {
        User user = iCacheDemo.selectUserByName(name);
        return user;
    }
}
