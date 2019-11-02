package com.eastday.demo.controller;

import com.eastday.demo.user.RetDto;
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
     * @param phone
     * @param code
     * @return
     */
    @RequestMapping(value="login")
    public RetDto login(String phone, String code){
        String url="http://user-provider/login?phone="+phone+"&code="+code;
        RetDto retDto=restTemplate.getForObject(url,RetDto.class);
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
