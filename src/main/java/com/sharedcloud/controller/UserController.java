package com.sharedcloud.controller;

import com.sharedcloud.Util.TokenUtil;
import com.sharedcloud.biz.TokenBiz;
import com.sharedcloud.biz.UserBiz;
import com.sharedcloud.dao.TokenDao;
import com.sharedcloud.dao.UserDao;
import com.sharedcloud.entity.Token;
import com.sharedcloud.entity.User;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: kxr
 * @Date: 2019/9/2
 * @Description
 */

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserBiz userBiz;

    @Autowired
    TokenDao tokenDao;

    @Autowired
    TokenBiz tokenBiz;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestBody User user,HttpServletRequest request) throws Exception {
        Boolean login = userBiz.login(user);
        if(login){
            String token = TokenUtil.getToken(user.getAccount());
            Token byAccount = tokenDao.getByAccount(user.getAccount());
            if( byAccount != null){
                byAccount.setStatus(9);
                tokenDao.update(byAccount);
            }
            Token newToken = new Token();
            newToken.setAccount(user.getAccount());
            newToken.setToken(token);
            tokenBiz.insert(newToken);
            return token;
        }
        return null;
    }

    @RequestMapping(value = "/getUserPath",method = RequestMethod.POST)
    public String getUserPath(HttpServletRequest httpServletRequest){
        String token = new String(httpServletRequest.getHeader("ACCESS_TOKEN"));
        String account = new TokenUtil().getAccountByToken(token);

        return userBiz.getByAccount(account).getUserpath();
    }

    @RequestMapping(value = "getUserListByAccount",method = RequestMethod.POST)
    public List<User> getUserListByAccount(@RequestParam(value = "account",required = true) String account){
        return userBiz.getUserListByAccount(account);
    }

    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    public User getUserInfo(HttpServletRequest httpServletRequest){
        String token = new String(httpServletRequest.getHeader("ACCESS_TOKEN"));
        String account = new TokenUtil().getAccountByToken(token);
        User u = userBiz.getByAccount(account);
        User user = new User();
        user.setAccount(account);
        user.setImgurl(u.getImgurl());
        user.setUsername(u.getUsername());
        user.setUserpath(u.getUserpath());
        return user;
    }
    //用户退出登陆，直接注销token
    @RequestMapping(value = "quit",method = RequestMethod.POST)
    public Boolean quit(HttpServletRequest httpServletRequest){
        String token = new String(httpServletRequest.getHeader("ACCESS_TOKEN"));
        String account = new TokenUtil().getAccountByToken(token);
        Token tokenBizByAccount = tokenBiz.getByAccount(account);
        tokenBizByAccount.setStatus(9);
        tokenBiz.update(tokenBizByAccount);
        return true;
    }
}
