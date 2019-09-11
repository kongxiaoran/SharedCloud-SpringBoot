package com.sharedcloud.biz;

import com.sharedcloud.Util.UserUtil;
import com.sharedcloud.dao.UserDao;
import com.sharedcloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: kxr
 * @Date: 2019/9/2
 * @Description
 */
@Component(value = "userBiz")
public class UserBiz {

    @Autowired
    private UserDao userDao;

    //判断用户是否可以登陆
    public Boolean login(User user) throws Exception {
        User u = userDao.getByAccount(user.getAccount());
        //将用户发送过来的加密密码进行解密
        user.setPassword(UserUtil.desEncrypt(user.getPassword(),"1254122559201909","1472583691472583"));
        if(user.getPassword().equals(u.getPassword())){
            return true;
        }
        return false;
    }

    public User getByAccount(String account){
        return userDao.getByAccount(account);
    }

    public List<User> getUserListByAccount(String account) {
        if(account == null){
            return null;
        }
        return userDao.getUserListByAccount(account);
    }
}
