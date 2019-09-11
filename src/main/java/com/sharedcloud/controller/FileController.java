package com.sharedcloud.controller;

import com.sharedcloud.Util.FileUtil;
import com.sharedcloud.biz.SharedBiz;
import com.sharedcloud.entity.MyFile;
import com.sharedcloud.entity.Shared;
import com.sharedcloud.model.SharedReq;
import com.sun.org.apache.regexp.internal.RE;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * @Author: kxr
 * @Date: 2019/9/2
 * @Description
 */
@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    SharedBiz sharedBiz;

    //显示该路径下所有文件及文件夹
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public List<MyFile> list(@RequestParam("path") String path) throws IOException {
        return FileUtil.list(path);
    }

    @RequestMapping(value = "/getByPath",method = RequestMethod.POST)
    public MyFile getByPath(@RequestParam("path") String path) throws IOException {
        return FileUtil.getByPath(path);
    }

    //新建文件夹
    @RequestMapping(value = "/mkdir",method = RequestMethod.POST)
    public boolean mkdir(@RequestParam("path") String path){
        return FileUtil.mkdir(path);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public String delete(@RequestBody String[] path){
        return FileUtil.delete(path);
    }

    @RequestMapping(value = "/deleteOver",method = RequestMethod.POST)
    public String  deleteOver(@RequestBody String[] path){
        return FileUtil.deleteOver(path);
    }

    @RequestMapping(value = "/rename",method = RequestMethod.POST)
    public String  rename(@RequestBody String map){
        JSONObject mapJson=JSONObject.fromObject(map);
        String oldfilename = mapJson.getString("oldfilename");
        String refilename = mapJson.getString("refilename");
        return FileUtil.rename(oldfilename,refilename);
    }

    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    public boolean uploadFile(@RequestParam("files") MultipartFile[] files,@RequestParam(value = "path",required = true)String path){

        File desFile = new File(path);
        if(!desFile.getParentFile().exists()){
            desFile.getParentFile().mkdirs();   //创建父级文件路径
        }
        for(MultipartFile m : files){
            String filePath = desFile.getPath()+"/"+m.getOriginalFilename();
            File newFile = new File(filePath);
            try {
                m.transferTo(newFile);
            }catch (IllegalStateException | IOException e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @RequestMapping(value = "/listByType",method = RequestMethod.POST)
    public List<MyFile> listByType(@RequestBody String map){
        JSONObject mapJson=JSONObject.fromObject(map);
        JSONArray typeArray = mapJson.getJSONArray("type");
        String[] type = (String[]) JSONArray.toArray(typeArray,String.class);
        String path = mapJson.getString("path");
        if(type[0].equals("*")){
            return FileUtil.list(path);
        }
//        JSONArray path = mapJson.getJSONArray("path");
//        String[] type = (typeString.substring(1, typeString.length() - 1)).split(",");//string数组
        return FileUtil.listByType(path,type);
    }

    @RequestMapping(value = "/listByName",method = RequestMethod.POST)
    public List<MyFile> listByName(@RequestBody String map){
        JSONObject mapJson=JSONObject.fromObject(map);
        String fileName = mapJson.getString("fileName");
        String path = mapJson.getString("path");
        if(fileName!=null&&path!=null){
            return FileUtil.listByName(path,fileName);
        }
        return null;
    }

    @RequestMapping(value = "/downloadFiles",method = RequestMethod.POST)
    public String downloadFiles(@RequestBody String[] paths,HttpServletRequest request,HttpServletResponse res) throws IOException {
//        res.setContentType("text/html; charset=UTF-8"); //设置编码字符
//        res.setContentType("application/octet-stream"); //设置内容类型为下载类型
//        res.setContentType("application/x-download");
//        OutputStream out = res.getOutputStream();   //创建页面返回方式为输出流，会
        // 自动弹出下载框
        return FileUtil.downloadFiles(paths,request,res);
    }

    @RequestMapping(value = "/downloadShareFiles",method = RequestMethod.POST)
    public String downloadShareFiles(@RequestBody SharedReq sharedReq,HttpServletRequest request,HttpServletResponse res) throws IOException {
        Map<String,Object> map = new HashMap<>();
        map.put("shareUrl",sharedReq.getShareUrl());
        map.put("accessCode",sharedReq.getAccessCode());
        Shared by = sharedBiz.getBy(map);
        if(by == null){
            return null;
        }
        return FileUtil.downloadFiles(new String[]{by.getUrl()},request,res);
    }



}
