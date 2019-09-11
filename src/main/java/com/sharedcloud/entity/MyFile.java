package com.sharedcloud.entity;

import java.io.File;

/**
 * @Author: kxr
 * @Date: 2019/9/2
 * @Description
 */
public class MyFile {

    private File file;
    private long size;       //MB单位
    private String countsize;
    private String name;        //文件名
    private String type;        //文件类型（文件（zip、txt、doc）、文件夹）
    private String path;        //文件的绝对路径
    private String relatePath;  //文件处于用户目录的路径

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountsize() {
        return countsize;
    }

    public void setCountsize(String countsize) {
        this.countsize = countsize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRelatePath() {
        return relatePath;
    }

    public void setRelatePath(String relatePath) {
        this.relatePath = relatePath;
    }
}
