package com.eastday.demo.client;

import com.eastday.demo.config.FeignConfig;
import com.eastday.demo.news.CmsTemplate;
import com.eastday.demo.user.RetDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@FeignClient(name = "user-provider",configuration=FeignConfig.class)
public interface TemplateClient {

    @PostMapping(value = "/template/addTemplate",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    RetDto addTemplate(@RequestPart("file") MultipartFile file, @RequestParam("templateName") String templateName);

    @PostMapping(value = "/template/testTemplate")
    RetDto testTemplate(@RequestParam("templateId") String templateId);

    @GetMapping(value = "/template/findAllTemplate")
    Map<String,Object> findAllTemplate(@RequestBody CmsTemplate cmsTemplate,@RequestParam("page") Integer page,@RequestParam("rows") Integer rows);

    @PostMapping(value = "/template/deleteTemplate")
    RetDto deleteTemplate(@RequestParam("templateId") String templateId);

    @PostMapping(value = "/template/updateTemplate")
    RetDto updateTemplate(@RequestBody CmsTemplate cmsTemplate);

    @GetMapping(value = "/template/findTemplateById")
    String findTemplateById(@RequestParam("templateId") String templateId);

    @PostMapping(value = "/template/getAllTemplate")
    List<CmsTemplate> getAllTemplate();
}
