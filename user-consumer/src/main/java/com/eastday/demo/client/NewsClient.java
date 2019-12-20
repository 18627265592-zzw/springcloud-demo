package com.eastday.demo.client;

import com.eastday.demo.config.FeignConfig;
import com.eastday.demo.news.CmsNews;
import com.eastday.demo.user.RetDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "user-provider",configuration=FeignConfig.class)
public interface NewsClient {

    @PostMapping(value = "/news/addNews")
    RetDto addNews(@RequestBody CmsNews cmsNews);

    @GetMapping(value = "/news/getAllNews")
    Map<String,Object> getAllNews(@RequestBody CmsNews cmsNews, @RequestParam("page") Integer page,@RequestParam("rows") Integer rows);

    @PostMapping(value = "/news/checkNews")
    RetDto checkNews(@RequestParam("newsId") String newsId,@RequestParam("str") String str);

    @GetMapping(value = "/news/previewNews")
    String previewNews(@RequestParam("newsId") String newsId);

    @PostMapping(value = "/news/updateNews")
    RetDto updateNews(@RequestBody CmsNews cmsNews);
}
