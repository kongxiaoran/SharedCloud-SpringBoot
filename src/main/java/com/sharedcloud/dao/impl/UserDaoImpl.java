package com.sharedcloud.dao.impl;

import com.sharedcloud.dao.UserDao;
import com.sharedcloud.entity.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: kxr
 * @Date: 2019/9/2
 * @Description
 */
@Repository(value = "userDao")
public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao{

    @Autowired
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public SqlSession getSqlSession() {
        return super.getSqlSession();
    }

    public User getByAccount(String account){
        return this.getSqlSession().selectOne("com.sharedcloud.dao.impl.UserDaoImpl.getByAccount",account);
    }

    @Override
    public List<User> getUserListByAccount(String account) {
        return this.getSqlSession().selectList("com.sharedcloud.dao.impl.UserDaoImpl.getUserListByAccount",account);
    }

}
