package com.sharedcloud.controller;

import com.sharedcloud.Util.FileUtil;
import com.sharedcloud.Util.ShareUtil;
import com.sharedcloud.biz.SharedBiz;
import com.sharedcloud.biz.UserBiz;
import com.sharedcloud.entity.MyFile;
import com.sharedcloud.entity.Shared;
import com.sharedcloud.entity.User;
import com.sharedcloud.model.SharedReq;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: kxr
 * @Date: 2019/9/7
 * @Description
 */
@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/api/share")
public class SharedController {

    @Autowired
    UserBiz userBiz;

    @Autowired
    SharedBiz sharedBiz;

    @RequestMapping(value = "/my/list",method = RequestMethod.POST)
    public List<MyFile> myList(@RequestParam(value = "account",required = true) String account){
        User user = userBiz.getByAccount(account);
        if(user == null){
            return null;
        }
        List<String> urlList = sharedBiz.listUrlByMy(user.getId());
        List<MyFile> fileList = new ArrayList<MyFile>();
        for(String url : urlList){
            File file = new File(url);
            MyFile newFile = new MyFile();
            newFile.setPath(file.getPath());
            newFile.setName(file.getName());
            if(file.isDirectory()){
                newFile.setType("文件夹");
            }else{
                newFile.setSize(file.length());
                newFile.setCountsize(FileUtil.countSize(newFile.getSize()));
                String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                newFile.setType(suffix);
            }
            fileList.add(newFile);
        }
        return fileList;
    }

    @RequestMapping(value = "/other/list",method = RequestMethod.POST)
    public List<MyFile> otherList(@RequestParam(value = "account",required = true) String account){
        User user = userBiz.getByAccount(account);
        if(user == null){
            return null;
        }
        List<String> urlList = sharedBiz.listUrlByOther(user.getId());
        List<MyFile> fileList = new ArrayList<MyFile>();
        for(String url : urlList){
            File file = new File(url);
            MyFile newFile = new MyFile();
            newFile.setPath(file.getPath());
            newFile.setName(file.getName());
            if(file.isDirectory()){
                newFile.setType("文件夹");
            }else{
                newFile.setSize(file.length());
                newFile.setCountsize(FileUtil.countSize(newFile.getSize()));
                String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                newFile.setType(suffix);
            }
            fileList.add(newFile);
        }
        return fileList;
    }

    @RequestMapping(value = "/enableDelete",method = RequestMethod.POST)
    public Boolean enableDelete(@RequestBody  SharedReq sharedReq){
        for (String path : sharedReq.getPath()){
            if(sharedBiz.enableDelete(path,sharedReq.getAccount())){
                Shared shared = new Shared();
                shared.setUrl(path);
                shared.setStatus(9);
                sharedBiz.update(shared);
            }else{
                return false;
            }
        }
        return true;
    }
    @RequestMapping(value = "/getFileSharingInfo",method = RequestMethod.POST)
    public String getFileSharingInfo(@RequestBody  SharedReq sharedReq){
        Map<String,Object> map = new HashMap<>();
        map.put("shareUrl",sharedReq.getShareUrl());
        map.put("accessCode",sharedReq.getAccessCode());
        Shared by = sharedBiz.getBy(map);
        if (by != null){
            File file = new File(by.getUrl());
            return file.getName();
        }else{
            return "-1";       //没有该分享文件
        }
    }

    //分享文件      每次分享一个
    @RequestMapping(value = "/setFileSharing",method = RequestMethod.POST)
    public Shared setFileSharing(@RequestBody SharedReq sharing,HttpServletRequest request){
        if(sharing.getPath().length == 0){
            return null;
        }
        User u = userBiz.getByAccount(sharing.getAccount());
        if(u == null){
            return null;
        }
        for(String path : sharing.getPath()){
            Shared shared = new Shared();
            shared.setUrl(path);
            shared.setUserId(u.getId());
            Shared by = sharedBiz.getBy(shared.getMap());
            if(by!=null){
                    return by;
            }else{
                shared.setPermission("r");
                shared.setShareType("*");
                shared.setShareUser(0l);
                shared.setShareUrl(ShareUtil.randomCode(20));
                shared.setAccessCode(ShareUtil.randomCode(4));
                long id = sharedBiz.insert(shared);
                shared.setId(id);
                return shared;
            }
        }
        return null;
    }

    //设置共享文件
    @RequestMapping(value = "/setFileShared",method = RequestMethod.POST)
    public Boolean setFileShared(@RequestBody SharedReq sharedReq, HttpServletRequest httpServletRequest){
        if(sharedReq.getPath().length == 0){
            return false;
        }
        User u = userBiz.getByAccount(sharedReq.getAccount());
        if(u == null){
            return false;
        }

        for(String path : sharedReq.getPath()){
            for(long sharedUser : sharedReq.getShareUser()){
                Shared shared = new Shared();
                shared.setUserId(u.getId());
                shared.setUrl(path);
                shared.setShareUser(sharedUser);
                Shared by = sharedBiz.getBy(shared.getMap());
                if(by != null){

                }else {
                    shared.setPermission(sharedReq.getPermission());
                    shared.setShareType(sharedReq.getShareType());
                    shared.setShareUser(sharedUser);
                    shared.setShareUrl(ShareUtil.randomCode(20));
                    shared.setAccessCode(ShareUtil.randomCode(4));
                    sharedBiz.insert(shared);
                }
            }
        }
        return true;
    }

//    //通过共享文件 共享链接和提取码 获取文件地址
//    @RequestMapping(value = "/getFileSharing",method = RequestMethod.POST)
//    public String


}
