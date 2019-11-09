package com.eastday.demo.client;

import com.eastday.demo.config.FeignConfig;
import com.eastday.demo.user.Menu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "user-provider",configuration=FeignConfig.class)
public interface MenuClient {

    @GetMapping(value = "/menu/{uid}")
    List<Menu> findMenuByUserId(@PathVariable(name = "uid") Integer uid);

    @GetMapping(value = "/menu/roleAndMenu")
    Map<Object,Object> findRoleAndMenu();

    @PostMapping(value = "/menu/role")
    String addRole(String roleName);

    @GetMapping(value = "/menu/role/{roleId}")
    List<Menu> findMenuByRoleId(@PathVariable("roleId") Integer roleId);

    @PostMapping(value = "/menu/role/{roleId}/{menuIds}")
    String updateMenuByRoleId(@PathVariable("roleId") Integer roleId, @PathVariable("menuIds") String menuIds);

    @PutMapping(value = "/menu/role/{userId}/{roleId}")
    String updateUserAndRole(@PathVariable("userId") Integer userId, @PathVariable("roleId") Integer roleId);
}
