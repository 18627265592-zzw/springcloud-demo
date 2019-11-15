package com.eastday.demo.controller;

import com.eastday.demo.client.SortClient;
import com.eastday.demo.news.Sort;
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

    @PostMapping(value = "")
    public RetDto addSort(@RequestParam("sortNames") String[] sortNames){
        return sortClient.addSort(sortNames);
    }

    @GetMapping(value = "")
    public List<Sort> getAllSort(){
        return sortClient.getAllSort();
    }

    @PostMapping(value = "deleteBySortId")
    public RetDto deleteBySortId(@RequestParam("sortId") Integer sortId){
        return sortClient.deleteBySortId(sortId);
    }

    @PostMapping(value = "addSortSon")
    public RetDto addSortSon(@RequestParam("sortId") Integer sortId,@RequestParam("sortName") String sortName){
        return sortClient.addSortSon(sortId,sortName);
    }
}
