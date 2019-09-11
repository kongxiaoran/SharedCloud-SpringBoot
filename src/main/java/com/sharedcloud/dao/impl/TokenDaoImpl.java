package com.sharedcloud.dao.impl;

import com.sharedcloud.dao.TokenDao;
import com.sharedcloud.entity.Token;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Author: kxr
 * @Date: 2019/9/2
 * @Description
 */
@Repository(value = "tokenDao")
public class TokenDaoImpl extends SqlSessionDaoSupport implements TokenDao {

    @Autowired
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public SqlSession getSqlSession() {
        return super.getSqlSession();
    }


    public long insert(Token token) {
        int insert = this.getSqlSession().insert("com.sharedcloud.dao.impl.TokenDaoImpl.insert", token);
        return insert;
    }

    public void update(Token token){
        this.getSqlSession().update("com.sharedcloud.dao.impl.TokenDaoImpl.update",token);
    }

    @Override
    public Token getByAccount(String account) {
        return this.getSqlSession().selectOne("com.sharedcloud.dao.impl.TokenDaoImpl.getByAccount",account);
    }
}
