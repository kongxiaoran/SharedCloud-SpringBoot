package com.sharedcloud.Util;
import com.sharedcloud.SharedcloudApplication;
import com.sharedcloud.biz.TokenBiz;
import com.sharedcloud.dao.TokenDao;
import com.sharedcloud.entity.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;


/**
 * @Author: kxr
 * @Date: 2019/9/2
 * @Description
 */
@Component
public class TokenUtil {


    public static String getToken(String account){
        Base64.Encoder encoder = Base64.getEncoder();
        String encodeA = encoder.encodeToString(account.getBytes());
        return UUID.randomUUID().toString().replace("-", "")
                +"%"+encodeA+"&"+UUID.randomUUID().toString().replace("-", "")
                +"%"+"sss";
    }

    public static String getAccountByToken(String token){
        String[] s = token.split("&|%");
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(s[1]));
    }


    public static void main(String[] args) {


//        new TokenUtil().checkToken(new TokenUtil().getToken("111","222"));
    }
}
