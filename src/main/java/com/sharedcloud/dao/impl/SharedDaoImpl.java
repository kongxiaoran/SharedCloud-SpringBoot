package com.sharedcloud.dao.impl;

import com.sharedcloud.dao.SharedDao;
import com.sharedcloud.entity.Shared;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: kxr
 * @Date: 2019/9/2
 * @Description
 */
@Repository(value = "sharedDao")
public class SharedDaoImpl extends SqlSessionDaoSupport implements SharedDao {

    @Autowired
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public SqlSession getSqlSession() {
        return super.getSqlSession();
    }

    @Override
    public long insert(Shared shared) {
        int insert = this.getSqlSession().insert("com.sharedcloud.dao.impl.SharedDaoImpl.insert", shared);
        return insert;
    }

    @Override
    public void update(Shared shared) {
        this.getSqlSession().update("com.sharedcloud.dao.impl.SharedDaoImpl.update",shared);
    }


    public Shared getBy(Map<String,Object> map) {
        return this.getSqlSession().selectOne("com.sharedcloud.dao.impl.SharedDaoImpl.getBy",map);
    }

    public List<Shared> listBy(long userId) {
        return this.getSqlSession().selectList("com.sharedcloud.dao.impl.SharedDaoImpl.listBy",userId);
    }

    @Override
    public List<String> listUrlByMy(long userId) {
        return this.getSqlSession().selectList("com.sharedcloud.dao.impl.SharedDaoImpl.listUrlByMy",userId);
    }

    public List<String> listUrlByOther(long shareUser) {
        return this.getSqlSession().selectList("com.sharedcloud.dao.impl.SharedDaoImpl.listUrlByOther",shareUser);
    }
}
