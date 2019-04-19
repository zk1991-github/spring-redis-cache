package com.github.zk.springbootrediscache.entity;

/**
 * @Author zk
 * @Date 2019/4/18 22:43
 */
public class User {
    private int id;
    private String name;
    private Long time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
