package com.eastday.demo.controller;

import com.eastday.demo.client.SortClient;
import com.eastday.demo.user.Sort;
import com.eastday.demo.user.RetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping(value = "")
    public RetDto addSort(@RequestParam("sortNames") String[] sortNames){
        return sortClient.addSort(sortNames);
    }


    /**
     * 查询所有栏目
     * @return
     */
    @GetMapping(value = "")
    public List<Sort> getAllSort(){
        return sortClient.getAllSort();
    }


    /**
     * 删除栏目
     * @param sortId 栏目id
     * @return
     */
    @PostMapping(value = "deleteBySortId")
    public RetDto deleteBySortId(@RequestParam("sortId") Integer sortId){
        return sortClient.deleteBySortId(sortId);
    }


    /**
     * 添加子栏目
     * @param sortId 父栏目id
     * @param sortName 子栏目名称
     * @return
     */
    @PostMapping(value = "addSortSon")
    public RetDto addSortSon(@RequestParam("sortId") Integer sortId,@RequestParam("sortName") String sortName){
        return sortClient.addSortSon(sortId,sortName);
    }
}
