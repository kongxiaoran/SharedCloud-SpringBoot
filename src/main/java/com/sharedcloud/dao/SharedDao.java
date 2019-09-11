package com.sharedcloud.dao;

import com.sharedcloud.entity.Shared;

import java.util.List;
import java.util.Map;

/**
 * @Author: kxr
 * @Date: 2019/9/2
 * @Description
 */

public interface SharedDao {
    public long insert(Shared token);

    public void update(Shared token);

//    public Shared getBy(String account);

    public Shared getBy(Map<String,Object> map);

    public List<Shared> listBy(long userId);

    public List<String> listUrlByMy(long userId);

    public List<String> listUrlByOther(long shareUser);
}
