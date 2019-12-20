package com.eastday.demo.controller;

import com.eastday.demo.client.SortClient;
import com.eastday.demo.config.Authentication;
import com.eastday.demo.config.SystemControllerLog;
import com.eastday.demo.config.UserLoginToken;
import com.eastday.demo.user.RetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/sort")
@EnableAutoConfiguration
public class SortController {

    @Autowired
    private SortClient sortClient;


    /**
     * 添加栏目
     * @param sortNames 栏目名数组
     * @return
     */
    @UserLoginToken
    @SystemControllerLog(type = 2,describe = "添加栏目")
    @Authentication(value = "sort_manage")
    @PostMapping(value = "")
    public RetDto addSort(String[] sortNames){
        return sortClient.addSort(sortNames);
    }


    /**
     * 查询所有栏目
     * @return
     */
    @UserLoginToken
    @SystemControllerLog(type = 2,describe = "查询所有栏目")
    @Authentication(value = "sort_manage")
    @GetMapping(value = "")
    public String getAllSort(){
        return sortClient.getAllSort();
    }


    /**
     * 删除栏目
     * @param sortId 栏目id
     * @return
     */
    @UserLoginToken
    @SystemControllerLog(type = 2,describe = "删除栏目")
    @Authentication(value = "sort_manage")
    @PostMapping(value = "deleteBySortId")
    public RetDto deleteBySortId(Integer sortId){
        return sortClient.deleteBySortId(sortId);
    }


    /**
     * 添加子栏目
     * @param sortId 父栏目id
     * @param sortNames 子栏目名称
     * @return
     */
    @UserLoginToken
    @SystemControllerLog(type = 2,describe = "添加子栏目")
    @Authentication(value = "sort_manage")
    @PostMapping(value = "addSortSon")
    public RetDto addSortSon(Integer sortId,String[] sortNames){
        return sortClient.addSortSon(sortId,sortNames);
    }
}
