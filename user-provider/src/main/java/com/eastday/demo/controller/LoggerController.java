package com.eastday.demo.controller;

import com.eastday.demo.service.LoggerService;
import com.eastday.demo.user.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/logger")
@EnableAutoConfiguration
public class LoggerController {

    @Autowired
    private LoggerService loggerService;

    /**
     * 内部调用接口，写入操作日志
     * @param logger
     */
    @PostMapping(value = "addLogger")
    public void addLogger(@RequestBody Logger logger){
        loggerService.addLogger(logger);
    }

    /**
     * 分页查询日志
     * @param page 页码
     * @param rows 每页数量
     * @return
     */
    @GetMapping(value = "findAllLogger")
    public Map<String,Object> findAllLogger(Integer page,Integer rows){
        return loggerService.findAllLogger(page,rows);
    }
}
