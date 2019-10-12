package com.dfw.demo.controller;

import com.dfw.demo.entity.RetDto;
import com.dfw.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping(value="/consumer")
@EnableAutoConfiguration
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     *手机号登录
     * @param user
     * @return
     */
    @RequestMapping(value="login")
    public RetDto login(User user){
        String url="http://user-provider/login?phone="+user.getPhone()+"&code="+user.getCode();
        RetDto retDto=restTemplate.postForObject(url,user,RetDto.class);
        return retDto;
    }

    /**
     * 模拟发送短信验证码
     * @param phone
     * @return
     */
    @RequestMapping(value="sendCode")
    public RetDto sendCode(String phone){
        String url="http://user-provider/sendCode?phone="+phone;
        System.out.println("url:"+url);
        RetDto retDto=restTemplate.getForObject(url,RetDto.class);
        return retDto;
    }

}
