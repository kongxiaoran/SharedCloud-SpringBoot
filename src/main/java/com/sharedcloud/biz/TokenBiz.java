package com.sharedcloud.biz;

import com.sharedcloud.Util.TokenUtil;
import com.sharedcloud.dao.TokenDao;
import com.sharedcloud.entity.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: kxr
 * @Date: 2019/9/2
 * @Description
 */

@Component(value = "tokenBiz")
public class TokenBiz {

    @Autowired
    private TokenDao tokenDao;

    public void update(Token token){
        tokenDao.update(token);
    }

    public long insert(Token token){
        return tokenDao.insert(token);
    }

    public Token getByAccount(String token){
        return tokenDao.getByAccount(token);
    }

    public Boolean checkToken(String token){
        String account = TokenUtil.getAccountByToken(token);
        Token tokenBizByAccount = tokenDao.getByAccount(account);
        if(token.equals(tokenBizByAccount.getToken())){
            long time = tokenBizByAccount.getValidtime().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String nowDate = df.format(new Date());// new Date()为获取当前系统时间
            long nowTime = new Date().getTime();
            if((int)(nowTime-time)/(1000)>60*60*2){
                return false;
            }
            return true;
        }
        return false;
    }

    public void delayToken(String token){
        String account = TokenUtil.getAccountByToken(token);
        Token tokenBizByAccount = tokenDao.getByAccount(account);
        long time = tokenBizByAccount.getValidtime().getTime();
        time+=1000*30;                                                  //延迟30秒
        tokenBizByAccount.setValidtime(new Date(time));
        tokenDao.update(tokenBizByAccount);
    }

}
