package com.github.zk.springbootrediscache.controller;

import com.github.zk.springbootrediscache.entity.User;
import com.github.zk.springbootrediscache.service.ICacheDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author zk
 * @Date 2019/4/18 22:51
 */
@RestController
@RequestMapping("/demo")
public class CacheDemoController {
    @Autowired
    private ICacheDemo iCacheDemo;

    @RequestMapping("/selectAllUser")
    public User selectAllUser() {
        return iCacheDemo.selectAllUser();
    }

    @RequestMapping("/selectUserById")
    public User selectUserById(@RequestParam int id,int flag) {
        User user = null;
        if (flag == 0) {
            user = iCacheDemo.selectUserById(id);
        } else if (flag == 1){
            user = iCacheDemo.selectUserById2(id);
        }
        return user;
    }

    @RequestMapping("/selectUserByKeyword")
    public User selectUserByKeyword(@RequestParam int id , String name) {
        User user = iCacheDemo.selectUserByKeyword(id , name);
        return user;
    }

    @RequestMapping("/selectUserByName")
    public User selectUserByName(@RequestParam String name) {
        User user = iCacheDemo.selectUserByName(name);
        return user;
    }

    @RequestMapping("/updateUserById")
    public User updateUserById(@RequestParam int id) {
        User user = iCacheDemo.updateUserById(id);
        return user;
    }

    @RequestMapping("/delUserById")
    public void delUserById(@RequestParam int id) {
        iCacheDemo.delUserById(id);
    }

    @RequestMapping("/delAllCacheTemp")
    public void delAllCacheTemp() {
        iCacheDemo.delAllCacheTemp();
    }

    @RequestMapping("/delUser")
    public void delUser(@RequestParam int... id) {
        iCacheDemo.delUser(id);
    }

}
