package com.sharedcloud.Util;

import com.sharedcloud.entity.MyFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author: kxr
 * @Date: 2019/9/2
 * @Description
 */
public class FileUtil {

    //新建文件
    public static File createNewFile(String path){
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    //新建文件夹
    public static boolean mkdir(String path){
        File file = new File(path);
        return file.mkdirs();
    }

    //文件重命名
    public static String rename(String oldPath,String newName){
        File file=new File(oldPath);
        if(file.exists()){
            File newfile=new File(file.getParent()+"/"+ newName);//创建新名字的抽象文件
            if(file.renameTo(newfile)) {
                return "重命名成功";
            }else{
                return "新文件名已存在";
            }
        }else{
            return "重命名文件不存在";
        }
    }

    public static MyFile getByPath(String path){
        File files = new File(path);
        if(files == null){
            return null;
        }
        MyFile myFile = new MyFile();
        myFile.setName(files.getName());
        myFile.setPath(files.getPath());
        String suffix = files.getName().substring(files.getName().lastIndexOf(".") + 1);
        myFile.setSize(files.length());
        myFile.setCountsize(FileUtil.countSize(myFile.getSize()));
        myFile.setType(suffix);
        return myFile;
    }
    //获取路径数组
    public static String[] getPaths(String path){
        String[] paths = path.split("/");
        return paths;
    }

    public static String countSize(long oldsize){
        DecimalFormat df = new DecimalFormat("0.0");
        if(oldsize>1024*1024*1024){
            float num= (float)oldsize/(1024*1024*1024);
            return df.format(num) + " GB";
        }else if(oldsize>1024*1024){
            float num= (float)oldsize/(1024*1024);
            return df.format(num) + " MB";
        }else{
            float num= (float)oldsize/(1024);
            return df.format(num) + " KB";
        }
    }

    //删除文件及空文件夹
    public static String delete(String[] pathList){
        for(String path:pathList){
            File file = new File(path);
            if(file.isDirectory()){
                if(file.listFiles().length>0){
                    return "非空文件夹不能直接删除";
                }
            }
            Boolean log = file.delete();
        }
        return "删除成功";
    }

    //删除路径下所有文件夹及文件
    public static String deleteOver(String[] pathList){
        for(String path:pathList){
            File directory = new File(path);
            if (!directory.isDirectory()){
                directory.delete();
            } else{
                File [] files = directory.listFiles();
                // 空文件夹
                if (files.length == 0){
                    directory.delete();
                }
                // 删除子文件夹和子文件
                for (File file : files){
                    if (file.isDirectory()){
                        deleteOver(new String[]{file.getPath()});
                    } else {
                        file.delete();
                    }
                }
                // 删除文件夹本身
                directory.delete();
            }
        }
        return "删除成功";
    }

    //获取路径下所有特定类型文件）(内层目录同样检索)
    public static List<MyFile> listByType(String path, String[] type){
        File files = new File(path);
        List<MyFile> list = new ArrayList<MyFile>();
        for(File f : files.listFiles()){
            if(f.isDirectory()){
                list.addAll(listByType(f.getPath(),type));
            }else {
                MyFile newFile = new MyFile();
                String suffix = f.getName().substring(f.getName().lastIndexOf(".") + 1);
                Boolean flag = false;
                for(String s : type){
                    if(s.equals(suffix)){
                        flag = true;
                    }
                }
                if(flag){
                    newFile.setPath(f.getPath());
                    newFile.setName(f.getName());
                    newFile.setSize(f.length());
                    newFile.setCountsize(FileUtil.countSize(newFile.getSize()));
                    newFile.setType(suffix);
                    list.add(newFile);
                }
            }
        }
        return list;
    }

    //获取路径下所有文件（内层目录不检索）
    public static List<MyFile> list(String path){
        System.out.println("path:"+path);
        File file = new File(path);
        List<MyFile> list = new ArrayList<MyFile>();
        File[] files = file.listFiles();
        if(files.length == 0){
            return null;
        }
        for(File f : files){
            MyFile myFile = new MyFile();
            myFile.setName(f.getName());
            myFile.setPath(f.getAbsolutePath());
            if(f.isDirectory()){
                myFile.setType("文件夹");
            }else{
                myFile.setSize(f.length());
                myFile.setCountsize(FileUtil.countSize(myFile.getSize()));
                String suffix = f.getName().substring(f.getName().lastIndexOf(".") + 1);
                myFile.setType(suffix);
            }
            list.add(myFile);
        }
        return list;
    }

    //通过文件名所有，路径下含有该子文件名的文件
    public static List<MyFile> listByName(String path,String fileName){
        File file = new File(path);
        List<MyFile> list = new ArrayList<MyFile>();
        File[] files = file.listFiles();

        for(File f : files){
            if(f.getName().contains(fileName)){
                MyFile newFile = new MyFile();
                newFile.setName(f.getName());
                newFile.setPath(f.getPath());
                newFile.setSize(f.length());
                newFile.setCountsize(FileUtil.countSize(newFile.getSize()));
                String suffix = f.getName().substring(f.getName().lastIndexOf(".") + 1);
                newFile.setType(suffix);
                list.add(newFile);
            }
            if(f.isDirectory()){
                list.addAll(listByName(f.getPath(),fileName));
            }
        }
        return list;
    }

    //创建该路径下的完成路径
    public static void createParentPath(File file) {
        File parentFile = file.getParentFile();
        if (null != parentFile && !parentFile.exists()) {
            parentFile.mkdirs(); // 创建文件夹
            createParentPath(parentFile); // 递归创建父级目录
        }
    }

    /**
     * 压缩文件（将多个文件打包成zip文件）
     *
     * @param zipBasePath   临时压缩文件基础路径
     * @param zipName   临时压缩文件名称
     * @param zipFilePath   临时压缩文件完整路径
     * @param filePaths 需要压缩的文件路径集合
     * @param zos
     * @return
     * @throws IOException
     */
    public static String zipFile(String zipBasePath, String zipName, String zipFilePath, List<String> filePaths, ZipOutputStream zos) throws IOException {

        //循环读取文件路径集合，获取每一个文件的路径
        for(String filePath : filePaths){
            File inputFile = new File(filePath);  //根据文件路径创建文件
            if(inputFile.exists()) { //判断文件是否存在
                if (inputFile.isFile()) {  //判断是否属于文件，还是文件夹
                    //创建输入流读取文件
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile));

                    //将文件写入zip内，即将文件进行打包
                    zos.putNextEntry(new ZipEntry(inputFile.getName()));

                    //写入文件的方法，同上
                    int size = 0;
                    byte[] buffer = new byte[1024];  //设置读取数据缓存大小
                    while ((size = bis.read(buffer)) > 0) {
                        zos.write(buffer, 0, size);
                    }
                    //关闭输入输出流
                    zos.closeEntry();
                    bis.close();

                } else {  //如果是文件夹，则使用穷举的方法获取文件，写入zip
                    try {
                        File[] files = inputFile.listFiles();
                        List<String> filePathsTem = new ArrayList<String>();
                        for (File fileTem:files) {
                            filePathsTem.add(fileTem.toString());
                        }
                        return zipFile(zipBasePath, zipName, zipFilePath, filePathsTem,zos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    //根据路径下载单个文件、或者打包下载多个文件
    public static String downloadFiles(String[] paths, HttpServletRequest request, HttpServletResponse res) throws IOException {
        res.setContentType("application/octet-stream"); //设置内容类型为下载类型
        OutputStream out = res.getOutputStream();   //创建页面返回方式为输出流，会
        if(paths.length == 1){
            File file = new File(paths[0]);  //创建文件
            res.setHeader("Content-disposition", "attachment;filename="+file.getName());//设置下载的文件名称
            FileInputStream in = new FileInputStream(file.getPath());//获取文件输入流

            try {
                int len = 0;
                byte[] buffer = new byte[1024*10];
                while ((len = in.read(buffer))!= -1) {
                    out.write(buffer,0,len);//将缓冲区的数据输出到客户端浏览器
                }
                out.flush();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                out.close();
                in.close();
            }
            return file.getName();
        }else{
            String zipBasePath  = new File(paths[0]).getParent();
            String zipName = "temp.zip";
            String zipFilePath = zipBasePath+File.separator+zipName;

            List<String> filePaths = new ArrayList<String>();
            for(String path : paths){
                filePaths.add(path);
            }

            //压缩文件
            File zip = new File(zipFilePath);
            if (!zip.exists()){
                zip.createNewFile();
            }
            //创建zip文件输出流
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip));
            FileUtil.zipFile(zipBasePath,zipName, zipFilePath,filePaths,zos);
            zos.close();
            res.setHeader("Content-disposition", "attachment;filename="+zipName);//设置下载的压缩文件名称
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(zipFilePath));
            byte[] buff = new byte[bis.available()];
            bis.read(buff);
            bis.close();
            out.write(buff);//输出数据文件
            out.flush();//释放缓存
            out.close();//关闭输出流
        }
        return null;
    }

    public static void main(String[] args) {
        new FileUtil().deleteOver(new String[]{"D:\\小猪猪\\test","D:\\小猪猪\\奥的"});
    }
}
