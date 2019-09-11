package com.sharedcloud.config;

import com.sharedcloud.annotation.IgnoreSecurity;
import com.sharedcloud.biz.TokenBiz;


import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;


/**
 * @Author: kxr
 * @Date: 2019/9/6
 * @Description
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    TokenBiz tokenBiz;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//         如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String requestPath = request.getRequestURI();
        if (requestPath.contains("/static") || requestPath.contains("/user/login")
                || requestPath.contains("/share/getFileSharingInfo") || requestPath.contains("/downloadShareFiles")) {
            return true;
        }
        if (requestPath.contains("/error")) {
            return true;
        }
        if (method.isAnnotationPresent(IgnoreSecurity.class)) {
            return true;
        }
        String token = request.getHeader("ACCESS_TOKEN");
        if (!tokenBiz.checkToken(token)) {
//            request.getRequestDispatcher("/login.jsp").forward(request, response);
//            response.getWriter().write(JsonUtil.objToJson(ServerResponse.createByErrorCodeAndMessage(ResponseCode.NEED_LOGIN.getCode(), "拦截器拦截，请登录")));
//            returnJson(response);
            return false;
        }
        tokenBiz.delayToken(token);
        return true;
    }

    private void returnJson(HttpServletResponse response){
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            Map<String, Object> result = new HashMap<>();
            result.put("message","token失效");
            writer.print(result);
        } catch (IOException e){

        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }



}
