package com.github.zk.springbootrediscache.controller;

import com.github.zk.springbootrediscache.entity.User;
import com.github.zk.springbootrediscache.service.ICacheDemo;
import com.github.zk.springbootrediscache.service.ICacheDemo2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zk
 * @Date 2019/4/18 22:51
 */
@RestController
@RequestMapping("/demo2")
public class CacheDemoController2 {
    @Autowired
    private ICacheDemo2 iCacheDemo;

    @RequestMapping("/selectUserById")
    public User selectUserById(@RequestParam int id , int flag) {
        User user = null;
        if (flag == 0) {
            user = iCacheDemo.selectUserById(id);
        } else {
            user = iCacheDemo.selectUserById2(id);
        }
        return user;
    }
}
