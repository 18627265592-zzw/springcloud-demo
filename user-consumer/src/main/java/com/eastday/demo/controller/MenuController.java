package com.eastday.demo.controller;

import com.eastday.demo.client.MenuClient;
import com.eastday.demo.user.Menu;
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
     * @param uid 用户id
     * @return 用户权限列表
     */
    @GetMapping(value = "/{uid}")
    public List<Menu> findMenuByUserId(@PathVariable("uid") Integer uid){
        return menuClient.findMenuByUserId(uid);
    }


    /**
     * 查询所有角色及对应菜单列表
     * @return 角色对象:权限名称集合
     */
    @GetMapping(value = "/roleAndMenu")
    public Map<Object,Object> findRoleAndMenu(){
        return menuClient.findRoleAndMenu();
    }


    /**
     * 新增角色
     * @param roleName 角色名称
     * @return 'true':新增成功  'false':新增失败
     */
    @PostMapping(value = "/role")
    public String addRole(String roleName){
        return menuClient.addRole(roleName);
    }


    /**
     * 查询角色对象权限信息
     * @param roleId
     * @return 权限对象集合
     */
    @GetMapping(value = "/role/{roleId}")
    public List<Menu> findMenuByRoleId(@PathVariable("roleId") Integer roleId){
        return menuClient.findMenuByRoleId(roleId);
    }


    /**
     * 角色权限调整
     * @param roleId 角色id
     * @param menuIds 权限id字符串，以','分割
     * @return true':调整成功  'false':调整失败
     */
    @PostMapping(value = "/role/{roleId}/{menuIds}")
    public String updateMenuByRoleId(@PathVariable("roleId") Integer roleId, @PathVariable("menuIds") String menuIds){
        return menuClient.updateMenuByRoleId(roleId,menuIds);
    }


    /**
     * 用户角色调整
     * @param userId 用户id
     * @param roleId 角色id
     * @return true':调整成功  'false':调整失败
     */
    @PutMapping(value = "/role/{userId}/{roleId}")
    public String updateUserAndRole(@PathVariable("userId") Integer userId, @PathVariable("roleId") Integer roleId){
        return menuClient.updateUserAndRole(userId,roleId);
    }
}
