package com.eastday.demo.controller;

import com.eastday.demo.client.MenuClient;
import com.eastday.demo.config.Authentication;
import com.eastday.demo.config.UserLoginToken;
import com.eastday.demo.user.Menu;
import com.eastday.demo.user.RetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/menu")
@EnableAutoConfiguration
public class MenuController {

    @Autowired
    private MenuClient menuClient;

    /**
     * 获取用户菜单权限列表
     * @param userId 用户id
     * @return 用户权限列表
     */
    @UserLoginToken
    @Authentication(value = "user_permission")
    @PostMapping(value = "")
    public List<Menu> findMenuByUserId(@RequestParam("userId") String userId){
        return menuClient.findMenuByUserId(userId);
    }


    /**
     * 查询所有角色及对应菜单列表
     * @return 角色对象:权限名称集合
     */
    @UserLoginToken
    @Authentication(value = "user_permission")
    @GetMapping(value = "/roleAndMenu")
    public Map<Object,Object> findRoleAndMenu(){
        return menuClient.findRoleAndMenu();
    }


    /**
     * 新增角色
     * @param roleName 角色名称
     * @return 'true':新增成功  'false':新增失败
     */
    @GetMapping(value = "/role")
    public RetDto addRole(String roleName){
        return menuClient.addRole(roleName);
    }


    /**
     * 查询角色对象权限信息
     * @param roleId
     * @return 权限对象集合
     */
    @UserLoginToken
    @Authentication(value = "user_permission")
    @PostMapping(value = "/role")
    public List<Menu> findMenuByRoleId(@RequestParam("roleId") Integer roleId){
        return menuClient.findMenuByRoleId(roleId);
    }


    /**
     * 角色权限调整
     * @param roleId 角色id
     * @param menuIds 权限id字符串，以','分割
     * @return true':调整成功  'false':调整失败
     */
    @UserLoginToken
    @Authentication(value = "user_permission")
    @PostMapping(value = "/roleAndMenu")
    public RetDto updateMenuByRoleId(@RequestParam("roleId") Integer roleId, @RequestParam("menuIds") String menuIds){
        return menuClient.updateMenuByRoleId(roleId,menuIds);
    }


    /**
     * 用户角色调整
     * @param userId 用户id
     * @param roleId 角色id
     * @return true':调整成功  'false':调整失败
     */
    @UserLoginToken
    @Authentication(value = "user_permission")
    @PostMapping(value = "/userAndRole")
    public RetDto updateUserAndRole(@RequestParam("userId") Integer userId, @RequestParam("roleId") Integer roleId){
        return menuClient.updateUserAndRole(userId,roleId);
    }
}
