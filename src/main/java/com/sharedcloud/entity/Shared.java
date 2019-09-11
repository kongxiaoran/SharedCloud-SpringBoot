package com.sharedcloud.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: kxr
 * @Date: 2019/9/2
 * @Description
 */
public class Shared {

    private Long id;
    private Long userId;
    private String permission;
    private String url;
    private String shareUrl;
    private Long shareUser;
    private String shareType;
    private String accessCode;
    private Integer status;

    public Map<String,Object> getMap(){
        Map<String, Object> params = new HashMap<>();
        params.put("userId",this.userId);
        params.put("url",this.url);
        params.put("shareUrl",this.shareUrl);
        params.put("accessCode",this.accessCode);
        params.put("shareUser",this.shareUser);
        return params;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Long getShareUser() {
        return shareUser;
    }

    public void setShareUser(Long shareUser) {
        this.shareUser = shareUser;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
