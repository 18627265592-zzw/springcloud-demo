package com.eastday.demo.controller;

import com.eastday.demo.client.UserClient;
import com.eastday.demo.user.RetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping(value="/consumer")
@EnableAutoConfiguration
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserClient userClient;



    /**
     *手机号登录
     * @param phone
     * @param code
     * @return
     */
    @PostMapping(value="smsLogin/{phone}/{code}")
    public RetDto smsLogin(@PathVariable(name = "phone") String phone,@PathVariable(name = "code") String code){
       /* String url="http://user-provider/user/smsLogin/"+phone+"/"+code;
        RetDto retDto=restTemplate.getForObject(url,RetDto.class);
        return retDto;*/
       return userClient.smsLogin(phone,code);
    }

    /**
     * 模拟发送短信验证码
     * @param phone
     * @return
     */
    @PostMapping(value="sendCode/{phone}")
    public RetDto sendCode(@PathVariable(name = "phone") String phone){
        /*String url="http://user-provider/user/sendCode/"+phone;
        System.out.println("url:"+url);
        RetDto retDto=restTemplate.getForObject(url,RetDto.class);
        return retDto;*/
        return userClient.sendCode(phone);
    }

}
