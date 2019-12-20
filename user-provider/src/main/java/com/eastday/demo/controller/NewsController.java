package com.eastday.demo.controller;

import com.eastday.demo.news.CmsNews;
import com.eastday.demo.service.NewsService;
import com.eastday.demo.user.RetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/news")
@EnableAutoConfiguration
public class NewsController {

    @Autowired
    NewsService newsService;

    /**
     * 创建新闻
     * @param cmsNews
     * @return
     */
    @PostMapping(value = "/addNews")
    public RetDto addNews(@RequestBody CmsNews cmsNews){
        return newsService.addNews(cmsNews);
    }

    /**
     * 分页查询新闻信息（最高管理员）
     * @param cmsNews 查询条件
     * @param page 页码
     * @param rows 每页显示数量
     * @return
     */
    @GetMapping(value = "/getAllNews")
    public Map<String,Object> getAllNews(@RequestBody CmsNews cmsNews,Integer page,Integer rows){
        return newsService.getAllNews(cmsNews,page,rows);
    }

    /**
     * 修改新闻状态
     * @param newsId 新闻id
     * @param str 发布：publish  审核成功：agree  审核失败：disagree  删除：delete
     * @return
     */
    @PostMapping(value = "/checkNews")
    public RetDto checkNews(String newsId,String str){
        return newsService.checkNews(newsId,str);
    }

    /**
     * 新闻预览
     * @param newsId 新闻id
     * @return
     */
    @GetMapping(value = "/previewNews")
    public String previewNews(String newsId){
        return newsService.previewNews(newsId);
    }

    /**
     * 修改新闻信息
     * @return
     */
    @PostMapping(value = "/updateNews")
    public RetDto updateNews(@RequestBody CmsNews cmsNews){
        return newsService.updateNews(cmsNews);
    }


}
