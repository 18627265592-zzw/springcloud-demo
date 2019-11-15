package com.eastday.demo.controller;

import com.eastday.demo.config.Authentication;
import com.eastday.demo.config.UserLoginToken;
import com.eastday.demo.service.MenuService;
import com.eastday.demo.user.Menu;
import com.eastday.demo.user.RetDto;
import com.eastday.demo.user.RoleAndMenu;
import com.eastday.demo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/menu")
@EnableAutoConfiguration
public class MenuController {

    @Autowired
    private MenuService menuService;

    //默认先经过过滤器校验是否登录，再aop验证权限
    /**
     * 获取用户菜单权限列表
     * @param userId 用户id
     * @return 用户权限列表
     */
    @PostMapping(value = "")
    @UserLoginToken
    @Authentication(value = "user_permission")
    public List<Menu> findMenuByUserId(String userId){
        //判断参数id与token中userId是否一致
        System.out.println(userId+"----------"+JwtUtils.getTokenUserId());
        if(JwtUtils.getTokenUserId().equals(userId)){
            return menuService.findMenuByUserId(userId);
        }else{
            return null;
        }
    }


    /**
     * 查询所有角色及对应菜单列表
     * @return 角色对象:权限名称集合
     */
    @GetMapping(value = "/roleAndMenu")
    @UserLoginToken
    @Authentication(value = "user_permission")
    public Map<Object,Object> findRoleAndMenu(){
        return menuService.findRoleAndMenu();
    }


    /**
     * 新增角色
     * @param roleName 角色名称
     * @return 'true':新增成功  'false':新增失败
     */
    @GetMapping(value = "/role")
    @UserLoginToken
    @Authentication(value = "user_permission")
    public RetDto addRole(String roleName){
        return menuService.addRole(roleName);
    }

    /**
     * 查询角色对象权限信息
     * @param roleId
     * @return 权限对象集合
     */
    @PostMapping(value = "/role")
    @UserLoginToken
    @Authentication(value = "user_permission")
    public List<Menu> findMenuByRoleId(Integer roleId){
        return menuService.findMenuByRoleId(roleId);
    }


    /**
     * 角色权限调整
     * @param roleId 角色id
     * @param menuIds 权限id数组
     * @return true':调整成功  'false':调整失败
     */
    @PostMapping(value = "/roleAndMenu")
    @UserLoginToken
    @Authentication(value = "user_permission")
    public RetDto updateMenuByRoleId(Integer roleId,String[] menuIds){
        List<RoleAndMenu> list = new ArrayList<>();
        for(int i=0;i<menuIds.length;i++){
            RoleAndMenu roleAndMenu = new RoleAndMenu();
            roleAndMenu.setMenuId(Integer.parseInt(menuIds[i]));
            roleAndMenu.setRoleId(roleId);
            roleAndMenu.setCreateTime(new Date());
            list.add(roleAndMenu);
        }
        return menuService.updateMenuByRoleId(list);
    }


    /**
     * 用户角色调整
     * @param userId 用户id
     * @param roleId 角色id
     * @return true':调整成功  'false':调整失败
     */
    @PostMapping(value = "/userAndRole")
    @UserLoginToken
    @Authentication(value = "user_permission")
    public RetDto updateUserAndRole(String userId,Integer roleId){
        return menuService.updateUserAndRole(userId,roleId);
    }



}
