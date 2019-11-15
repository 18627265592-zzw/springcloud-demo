package com.eastday.demo.config;

import com.eastday.demo.dao.IMenuDao;
import com.eastday.demo.user.Menu;
import com.eastday.demo.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@Slf4j
public class AuthenticationAspect {

    @Autowired
    private IMenuDao menuDao;

    @Pointcut("@annotation(authentication)")
    public void annotationPointCut(Authentication authentication) {

    }

    @Before("annotationPointCut(authentication)")
    public void before(Authentication authentication){
        String permission = authentication.value();
        //权限控制业务
        String userId=JwtUtils.getTokenUserId();
        List<Menu> menuList = menuDao.selectMenuByUserId(userId);
        List<String> menuCodeList=new ArrayList<>();
        for(Menu menu:menuList){
            menuCodeList.add(menu.getMenuCode());
        }
        log.debug(menuCodeList.toString()+"---------"+permission);
        if(!menuCodeList.contains(permission)){
            throw new RuntimeException("权限不足");
        }
    }
}
