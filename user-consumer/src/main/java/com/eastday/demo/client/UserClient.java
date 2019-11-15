package com.eastday.demo.client;

import com.eastday.demo.config.FeignConfig;
import com.eastday.demo.user.RetDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cms-login",configuration=FeignConfig.class)
public interface UserClient {

    @PostMapping(value="/user/smsLogin")
    RetDto smsLogin(@RequestParam("phone") String phone, @RequestParam("code") String code);

    @PostMapping(value="/user/sendCode")
    RetDto sendCode(@RequestParam("phone") String phone);

}
