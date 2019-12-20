package com.eastday.demo.controller;

import com.eastday.demo.client.LoggerClient;
import com.eastday.demo.config.Authentication;
import com.eastday.demo.config.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/logger")
@EnableAutoConfiguration
public class LoggerController {

    @Autowired
    private LoggerClient loggerClient;

    @UserLoginToken
    //@SystemControllerLog(type = 2,describe = "分页查询日志")
    @Authentication(value = "user_logger")
    @GetMapping(value = "findAllLogger")
    public Map<String,Object> findAllLogger(Integer page,Integer rows){
        return loggerClient.findAllLogger(page,rows);
    }

}
