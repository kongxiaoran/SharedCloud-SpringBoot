package com.sharedcloud.biz;

import com.sharedcloud.dao.SharedDao;
import com.sharedcloud.dao.UserDao;
import com.sharedcloud.entity.Shared;
import com.sharedcloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: kxr
 * @Date: 2019/9/7
 * @Description
 */
@Component(value = "sharedBiz")
public class SharedBiz {

    @Autowired
    SharedDao sharedDao;
    
    @Autowired
    UserDao userDao;

    public void update(Shared shared){
        sharedDao.update(shared);
    }
    
    public Boolean enableDelete(String path,String account){
        User u = userDao.getByAccount(account);
        Map<String,Object> map = new HashMap<>();
        map.put("shareUser",u.getId());
        map.put("url",path);
        Shared by = sharedDao.getBy(map);
        if(by == null){
            return false;
        }else{
            if(by.getPermission().equals("r/w")){
                return true;
            }else{
                return false;
            }
        }
    }

    public long insert(Shared shared){
        return sharedDao.insert(shared);
    }

    public Shared getBy(Map<String,Object> map){
        return sharedDao.getBy(map);
    }

    public List<Shared> listBy(long userId){
        return sharedDao.listBy(userId);
    }

    public List<String> listUrlByMy(long userId){
        return sharedDao.listUrlByMy(userId);
    }

    public List<String> listUrlByOther(long shareUserId){
        return sharedDao.listUrlByOther(shareUserId);
    }
}
