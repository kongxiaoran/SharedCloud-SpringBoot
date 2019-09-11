package com.sharedcloud.dao;

import com.sharedcloud.entity.User;

import java.util.List;

/**
 * @Author: kxr
 * @Date: 2019/9/2
 * @Description
 */
public interface UserDao {

    public User getByAccount(String account);

    List<User> getUserListByAccount(String account);
}
