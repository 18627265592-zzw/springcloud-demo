package com.eastday.demo.controller;

import com.eastday.demo.news.CmsTemplate;
import com.eastday.demo.service.TemplateService;
import com.eastday.demo.user.RetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/template")
@EnableAutoConfiguration
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    /**
     * 添加模板
     * @param file 模板文件
     * @param templateName 自定义模板名称
     * @return
     */
    @PostMapping(value = "addTemplate")
    public RetDto addTemplate(MultipartFile file, String templateName){
        return templateService.addTemplate(file,templateName);
    }

    /**
     * 生成静态化页面
     * @param templateId 模板id
     * @return
     */
    @PostMapping(value = "testTemplate")
    public RetDto testTemplate(String templateId){
        return templateService.testTemplate(templateId);
    }

    /**
     * 分页查询模板信息
     * @param cmsTemplate 查询条件
     * @param page 页码
     * @param rows 每页显示数量
     * @return
     */
    @GetMapping(value = "findAllTemplate")
    public Map<String,Object> findAllTemplate(@RequestBody CmsTemplate cmsTemplate,Integer page,Integer rows){
        return templateService.findAllTemplate(cmsTemplate,page,rows);
    }

    /**
     * 删除模板信息
     * @param templateId 模板id
     * @return
     */
    @PostMapping(value = "deleteTemplate")
    public RetDto deleteTemplate(String templateId){
        return templateService.deleteTemplate(templateId);
    }

    /**
     * 修改模板信息
     * @param cmsTemplate 修改参数
     * @return
     */
    @PostMapping(value = "updateTemplate")
    public RetDto updateTemplate(@RequestBody CmsTemplate cmsTemplate){
        return templateService.updateTemplate(cmsTemplate);
    }

    /**
     * 根据模板ID查询模板信息
     * @param templateId 模板ID
     * @return
     */
    @GetMapping(value = "findTemplateById")
    public String findTemplateById(String templateId){
        return templateService.findTemplateById(templateId);
    }

    /**
     * 查询所有模板
     * @return
     */
    @PostMapping(value = "getAllTemplate")
    public List<CmsTemplate> getAllTemplate(){
        return templateService.getAllTemplate();
    }
}
