package com.sharedcloud.model;

/**
 * @Author: kxr
 * @Date: 2019/9/8
 * @Description
 */
public class SharedReq {

    private String account;
    private String[] path;
    private String permission;
    private String url;
    private String shareType;
    private Long[] shareUser;
    private String shareUrl;
    private Long userId;
    private String accessCode;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
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

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public Long[] getShareUser() {
        return shareUser;
    }

    public void setShareUser(Long[] shareUser) {
        this.shareUser = shareUser;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}
