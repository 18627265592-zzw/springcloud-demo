package com.eastday.demo.client;

import com.eastday.demo.config.FeignConfig;
import com.eastday.demo.user.RetDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-provider",configuration=FeignConfig.class)
public interface UserClient {

    @PostMapping(value="/user/smsLogin/{phone}/{code}")
    RetDto smsLogin(@PathVariable("phone") String phone,@PathVariable("code") String code);

    @PostMapping(value="/user/sendCode/{phone}")
    RetDto sendCode(@PathVariable("phone") String phone);

}
