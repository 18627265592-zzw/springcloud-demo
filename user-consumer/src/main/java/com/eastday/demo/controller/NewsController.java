package com.eastday.demo.controller;

import com.eastday.demo.client.NewsClient;
import com.eastday.demo.config.Authentication;
import com.eastday.demo.config.SystemControllerLog;
import com.eastday.demo.config.UserLoginToken;
import com.eastday.demo.news.CmsNews;
import com.eastday.demo.user.RetDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(value="/news")
@EnableAutoConfiguration
public class NewsController {

    @Autowired
    NewsClient newsClient;

    /**
     * 创建新闻
     * @param cmsNews
     * @return
     */
    @UserLoginToken
    @SystemControllerLog(type = 2,describe = "发布新闻")
    @Authentication(value = "news_manage")
    @PostMapping(value = "/addNews")
    public RetDto addNews(CmsNews cmsNews){
        return newsClient.addNews(cmsNews);
    }

    /**
     * 分页查询新闻信息
     * @param cmsNews 查询条件
     * @param page 页码
     * @param rows 每页显示数量
     * @return
     */
    @UserLoginToken
    //@SystemControllerLog(type = 2,describe = "查询新闻信息")
    @Authentication(value = "news_manage")
    @GetMapping(value = "/getAllNews")
    public Map<String,Object> getAllNews(CmsNews cmsNews, Integer page, Integer rows){
        return newsClient.getAllNews(cmsNews,page,rows);
    }

    /**
     * 修改新闻状态
     * @param newsId 新闻id
     * @param str 发布：publish  审核成功：agree  审核失败：disagree  删除：delete
     * @return
     */
    @UserLoginToken
    @SystemControllerLog(type = 2,describe = "修改新闻状态")
    @Authentication(value = "news_manage")
    @PostMapping(value = "/checkNews")
    public RetDto checkNews(String newsId,String str){
        return newsClient.checkNews(newsId,str);
    }

    /**
     * 新闻预览
     * @param newsId 新闻id
     * @return
     */
    /*@UserLoginToken
    @SystemControllerLog(type = 2,describe = "新闻预览")
    @Authentication(value = "news_manage")*/
    @GetMapping(value = "/previewNews")
    public void previewNews(String newsId, HttpServletResponse response) throws IOException {
        String pageHtml = newsClient.previewNews(newsId);
        if(StringUtils.isBlank(pageHtml)){
            pageHtml="页面数据出错！！！";
        }
        //通过response对象将内容输出
        ServletOutputStream outputStream = response.getOutputStream();
        response.setHeader("Content-type","text/html;charset=utf-8");
        outputStream.write(pageHtml.getBytes("utf-8"));
    }

    /**
     * 修改新闻信息
     * @return
     */
    @UserLoginToken
    @SystemControllerLog(type = 2,describe = "新闻修改")
    @Authentication(value = "news_manage")
    @PostMapping(value = "/updateNews")
    public RetDto updateNews(CmsNews cmsNews){
        return newsClient.updateNews(cmsNews);
    }
}
