package com.eastday.demo.service;

import com.eastday.demo.dao.IMenuDao;
import com.eastday.demo.dao.IRoleAndMenuDao;
import com.eastday.demo.dao.IRoleDao;
import com.eastday.demo.dao.IUserAndRoleDao;
import com.eastday.demo.user.*;
import com.eastday.demo.utils.CmsUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service(value = "menuService")
public class MenuService {

    @Autowired
    private IMenuDao menuDao;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IRoleAndMenuDao roleAndMenuDao;

    @Autowired
    private IUserAndRoleDao userAndRoleDao;

    public List<Menu> findMenuByUserId(String userId){
        List<Menu> allMenus = menuDao.selectMenuByUserId(userId);
        List<Menu> menus=new ArrayList<>();
        for(Menu menu: allMenus){
            if(menu.getParentId()==0 && menu.getLever()==1){  //添加1级菜单
                menus.add(menu);
            }
        }
        for(Menu menu: menus){
            menu.setChildMenus(getChild(menu.getMenuId(),allMenus));  //为1级菜单添加2级菜单
        }
        return menus;
    }

    public List<Menu> getChild(int menuId,List<Menu> allMenus){
        List<Menu> childMenus=new ArrayList<>();
        for(Menu menu:allMenus){
            if(menu.getParentId()!=0){
                if(menu.getParentId()==menuId){
                    childMenus.add(menu);
                }
            }
        }
        if(childMenus.size()==0){
            return null;
        }
        return childMenus;
    }


    public Map<String,Object> findRoleAndMenu(Integer page,Integer rows){
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(page,rows);
        List<Role> roleList = roleDao.selectAll();
        for(Role role:roleList){
            List<String> roleNameList = new ArrayList<>();
            List<Menu> menus = menuDao.selectMenuByRoleId(role.getRoleId());
            for(Menu menu:menus){
                roleNameList.add(menu.getMenuName());
            }
            role.setMenus(roleNameList);
        }
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        map.put("total",pageInfo.getTotal());//总记录数
        map.put("rows",pageInfo.getList());//结果集
        System.out.println();
        return map;
    }

    public RetDto addRole(String roleName){
        try {
            Role role = new Role();
            role.setRoleName(roleName);
            Role role1 = roleDao.selectOne(role);
            if(role1 != null){
                return new RetDto(false, 2, null);// 2：角色名已存在;
            }else{
                role.setCreateTime(new Date());
                int i = roleDao.insertSelective(role);
                if(i == 1){
                    return new RetDto(true, 0, null);// 0：成功
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return new RetDto(false, 1, null);// 1：操作失败;
    }

    public String findAllMenu(){
        return CmsUtils.ObjectToString(menuDao.selectAll());
    }

    public List<Menu> findMenuByRoleId(Integer roleId){
        return menuDao.selectMenuByRoleId(roleId);
    }

    public RetDto updateMenuByRoleId(List<RoleAndMenu> list){
        try {
            if(list != null && !list.isEmpty()){
                RoleAndMenu roleAndMenu = new RoleAndMenu();
                //取roleId
                roleAndMenu.setRoleId(list.get(0).getRoleId());
                //先删除角色对应所有权限
                roleAndMenuDao.delete(roleAndMenu);
                //重新添加角色权限
                roleAndMenuDao.insertList(list);
                return new RetDto(true, 0, null);// 0：成功
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new RetDto(false, 1, null);// 1：操作失败;
    }


    public RetDto updateUserAndRole(String userId,Integer roleId){
        try {
            if(StringUtils.isNotBlank(userId) && roleId != 0){
                UserAndRole userAndRole = new UserAndRole();
                userAndRole.setUserId(userId);

                UserAndRole userAndRole1 = userAndRoleDao.selectOne(userAndRole);
                if(userAndRole1==null){
                    userAndRole.setRoleId(roleId);
                    userAndRole.setCreateTime(new Date());
                    userAndRoleDao.insertSelective(userAndRole);
                }else{
                    userAndRole1.setRoleId(roleId);
                    userAndRole1.setCreateTime(new Date());
                    userAndRoleDao.updateByPrimaryKeySelective(userAndRole1);
                }
                return new RetDto(true, 0, null);// 0：成功
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new RetDto(false, 1, null);// 1：操作失败
    }

    public List<Menu> selectMenuByUserId(String userId){
       return menuDao.selectMenuByUserId(userId);
    }

}
