package com.eastday.demo.client;

import com.eastday.demo.config.FeignConfig;
import com.eastday.demo.user.RetDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-provider",configuration=FeignConfig.class)
public interface SortClient {

    @PostMapping(value = "/sort")
    RetDto addSort(@RequestParam("sortNames") String[] sortNames);

    @GetMapping(value = "/sort")
    String getAllSort();

    @PostMapping(value = "/sort/deleteBySortId")
    RetDto deleteBySortId(@RequestParam("sortId") Integer sortId);

    @PostMapping(value = "/sort/addSortSon")
    RetDto addSortSon(@RequestParam("sortId") Integer sortId,@RequestParam("sortNames") String[] sortNames);
}
