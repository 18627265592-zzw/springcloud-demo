package com.eastday.demo.controller;

import com.eastday.demo.user.Sort;
import com.eastday.demo.service.SortService;
import com.eastday.demo.user.RetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/sort")
@EnableAutoConfiguration
public class SortController {

    @Autowired
    private SortService sortService;

    /**
     * 添加栏目
     * @param sortNames 栏目名数组
     * @return
     */
    @PostMapping(value = "")
    public RetDto addSort(String[] sortNames){
        List<Sort> list = new ArrayList<>();
        for(int i=0;i<sortNames.length;i++){
            Sort sort = new Sort();
            sort.setSortName(sortNames[i]);
            if(i == 0){
                //第1个为父栏目
                sort.setParentId(0);
                sort.setLever(1);//1级栏目
            }else{
                sort.setLever(2);//2级栏目
            }
            list.add(sort);
        }
        return sortService.addSort(list);
    }


    /**
     * 查询所有栏目
     * @return
     */
    @GetMapping(value = "")
    public String getAllSort(){
        return sortService.getAllSort();
    }

    /**
     * 删除栏目
     * @param sortId 栏目id
     * @return
     */
    @PostMapping(value = "deleteBySortId")
    public RetDto deleteBySortId(Integer sortId){
        return sortService.deleteBySortId(sortId);
    }

    /**
     * 添加子栏目
     * @param sortId 父栏目id
     * @param sortNames 子栏目名称
     * @return
     */
    @PostMapping(value = "addSortSon")
    public RetDto addSortSon(Integer sortId,String[] sortNames){
        List<Sort> list = new ArrayList<>();
        for(int i=0;i<sortNames.length;i++){
            Sort sort = new Sort();
            sort.setParentId(sortId);
            sort.setSortName(sortNames[i]);
            sort.setLever(2);//2级栏目
            sort.setCreateTime(new Date());
            list.add(sort);
        }
        return sortService.addSortSon(list);
    }
}
