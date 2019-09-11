package com.sharedcloud.dao;

import com.sharedcloud.entity.Token;

/**
 * @Author: kxr
 * @Date: 2019/9/2
 * @Description
 */
public interface TokenDao {

    public long insert(Token token);

    public void update(Token token);

    public Token getByAccount(String account);
}
