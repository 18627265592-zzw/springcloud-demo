package com.eastday.demo.config;

import com.eastday.demo.client.LoggerClient;
import com.eastday.demo.user.Logger;
import com.eastday.demo.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
public class LogInterceptorConfig implements HandlerInterceptor {

    @Autowired
    private LoggerClient loggerClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        SystemControllerLog systemlog = handlerMethod.getMethodAnnotation(SystemControllerLog.class);
        if(systemlog != null) {
            int type = systemlog.type();              //日志类型
            String describe = systemlog.describe();   //日志描述
            String remoteAddr=request.getRemoteAddr();//请求的IP
            String requestUri=request.getRequestURI();//请求的Uri
            String method=request.getMethod();        //请求的方法类型(post/get)
            String userId = "";//用户id
            Logger logger = new Logger();
            if(type == 1){  //登录操作,获取不到token
                if(request.getAttribute("userId") != null){
                    userId = request.getAttribute("userId").toString();
                }
            }else{
                userId = JwtUtils.getTokenUserId();
            }
            logger.setUserId(userId);
            logger.setDescribe(describe);
            logger.setRemoteAddr(remoteAddr);
            logger.setRequestUri(requestUri);
            logger.setMethod(method);
            logger.setType(type);
            logger.setCreateTime(new Date());
            if(type == 1 && StringUtils.isBlank(userId)){
                log.debug("未登录成功，不记录日志");
            }else{
                loggerClient.addLogger(logger);
            }

        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
