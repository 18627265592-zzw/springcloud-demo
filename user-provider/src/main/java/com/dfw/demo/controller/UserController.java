package com.dfw.demo.controller;

import com.dfw.demo.entity.RetDto;
import com.dfw.demo.entity.User;
import com.dfw.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableAutoConfiguration
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     *手机号登录
     * @param user
     * @return
     */
    @RequestMapping(value="login")
    public RetDto login(User user){
        return userService.login(user);
    }

    /**
     * 模拟发送短信验证码
     * @param phone
     * @return
     */
    @RequestMapping(value="sendCode")
    public RetDto sendCode(String phone){
        return userService.sendCode(phone);
    }

}
