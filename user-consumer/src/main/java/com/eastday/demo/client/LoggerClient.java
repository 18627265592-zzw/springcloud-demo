package com.eastday.demo.client;

import com.eastday.demo.config.FeignConfig;
import com.eastday.demo.user.Logger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "user-provider",configuration=FeignConfig.class)
public interface LoggerClient {

    @PostMapping(value = "/logger/addLogger")
    void addLogger(@RequestBody Logger logger);

    @GetMapping(value = "/logger/findAllLogger")
    Map<String,Object> findAllLogger(@RequestParam("page") Integer page,@RequestParam("rows") Integer rows);
}
