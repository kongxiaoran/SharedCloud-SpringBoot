package com.sharedcloud.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: kxr
 * @Date: 2019/9/2
 * @Description
 */
public class User {

    private Long id;
    private String account;
    private String password;
    private String username;
    private String permission;
    private String imgurl;
    private String userpath;
    private Integer status;

    public Map<String,Object> getMap(){
        Map<String, Object> params = new HashMap<>();
        params.put("id",this.id);
        params.put("account",this.account);
        params.put("username",this.username);
        return params;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserpath() {
        return userpath;
    }

    public void setUserpath(String userpath) {
        this.userpath = userpath;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
