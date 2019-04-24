package com.github.zk.springbootrediscache.service;

import com.github.zk.springbootrediscache.entity.User;

/**
 * @Author zk
 * @Date 2019/4/18 22:43
 */
public interface ICacheDemo {

    User selectUserById(int id);

    User selectUserByName(String name);
}
