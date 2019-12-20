package com.eastday.demo.client;

import com.eastday.demo.config.FeignConfig;
import com.eastday.demo.user.Menu;
import com.eastday.demo.user.RetDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "user-provider",configuration=FeignConfig.class)
public interface MenuClient {

    @PostMapping(value = "/menu")
    List<Menu> findMenuByUserId(@RequestParam("userId") String userId);

    @GetMapping(value = "/menu/roleAndMenu")
    Map<String,Object> findRoleAndMenu(@RequestParam("page") Integer page,@RequestParam("rows") Integer rows);

    @PostMapping(value = "/menu/all")
    String findAllMenu();

    @GetMapping(value = "/menu/role")
    RetDto addRole(@RequestParam("roleName") String roleName);

    @PostMapping(value = "/menu/role")
    List<Menu> findMenuByRoleId(@RequestParam("roleId") Integer roleId);

    @PostMapping(value = "/menu/roleAndMenu")
    RetDto updateMenuByRoleId(@RequestParam("roleId") Integer roleId, @RequestParam("menuIds") String menuIds);

    @PostMapping(value = "/menu/userAndRole")
    RetDto updateUserAndRole(@RequestParam("userId") String userId, @RequestParam("roleId") Integer roleId);

    @PostMapping(value = "/menu/selectMenuByUserId")
    List<Menu> selectMenuByUserId(@RequestParam("userId") String userId);
}
