package com.eastday.demo.client;

import com.eastday.demo.user.RetDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("user-provider")
public interface UserClient {

    @PostMapping(value="/user/smsLogin/{phone}/{code}")
    RetDto smsLogin(@PathVariable(name = "phone") String phone,@PathVariable(name = "code") String code);

    @PostMapping(value="/user/sendCode/{phone}")
    RetDto sendCode(@PathVariable(name = "phone") String phone);
}
