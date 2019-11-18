package com.eastday.demo.controller;

import com.eastday.demo.service.UserService;
import com.eastday.demo.user.RetDto;
import com.eastday.demo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/user")
@EnableAutoConfiguration
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 手机验证码登录
     *
     * @param phone
     * @param code
     * @return
     */
    @PostMapping(value = "smsLogin")
    public RetDto smsLogin(String phone, String code, HttpServletResponse response) {
        return userService.smsLogin(phone, code, response);
    }

    /**
     * 模拟发送短信验证码
     *
     * @param phone
     * @return
     */
    @PostMapping(value = "sendCode")
    public RetDto sendCode(String phone) {
        return userService.sendCode(phone);
    }

    /**
     * 内部接口，验证token调用
     * @param userId
     * @return
     */
    @PostMapping(value = "selectByPrimaryKey")
    public User selectByPrimaryKey(String userId){
        return userService.selectByPrimaryKey(userId);
    }
}