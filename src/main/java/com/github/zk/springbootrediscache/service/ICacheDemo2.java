package com.github.zk.springbootrediscache.service;

import com.github.zk.springbootrediscache.entity.User;

/**
 * @author zk
 * @date 2019/5/21 10:00
 */
public interface ICacheDemo2 {
    User selectUserById(int id);

    User selectUserById2(int id);
}
