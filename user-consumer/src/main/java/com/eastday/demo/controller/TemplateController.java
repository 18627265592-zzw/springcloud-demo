package com.eastday.demo.controller;

import com.eastday.demo.client.TemplateClient;
import com.eastday.demo.config.Authentication;
import com.eastday.demo.config.SystemControllerLog;
import com.eastday.demo.config.UserLoginToken;
import com.eastday.demo.news.CmsTemplate;
import com.eastday.demo.user.RetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/template")
@EnableAutoConfiguration
public class TemplateController {

    @Autowired
    private TemplateClient templateClient;

    /**
     * 添加模板
     * @param file 模板文件
     * @param templateName 自定义模板名称
     * @return
     */
    @UserLoginToken
    @SystemControllerLog(type = 2,describe = "添加模板")
    @Authentication(value = "template_manage")
    @PostMapping(value = "addTemplate")
    public RetDto addTemplate(MultipartFile file, String templateName){
        return templateClient.addTemplate(file,templateName);
    }

    /**
     * 静态化测试
     * @param templateId 模板id
     * @return
     */
    @PostMapping(value = "testTemplate")
    public RetDto testTemplate(String templateId){
        return templateClient.testTemplate(templateId);
    }

    /**
     * 分页查询模板信息
     * @param cmsTemplate 查询条件
     * @param page 页码
     * @param rows 每页显示数量
     * @return
     */
    @UserLoginToken
    //@SystemControllerLog(type = 2,describe = "分页查询模板信息")
    @Authentication(value = "template_manage")
    @GetMapping(value = "/findAllTemplate")
    public Map<String,Object> findAllTemplate(CmsTemplate cmsTemplate, Integer page, Integer rows){
        return templateClient.findAllTemplate(cmsTemplate,page,rows);
    }

    /**
     * 删除模板信息
     * @param templateId 模板id
     * @return
     */
    @UserLoginToken
    @SystemControllerLog(type = 2,describe = "删除模板")
    @Authentication(value = "template_manage")
    @PostMapping(value = "deleteTemplate")
    public RetDto deleteTemplate(String templateId){
        return templateClient.deleteTemplate(templateId);
    }

    /**
     * 修改模板信息
     * @param cmsTemplate 修改参数
     * @return
     */
    @UserLoginToken
    @SystemControllerLog(type = 2,describe = "修改模板")
    @Authentication(value = "template_manage")
    @PostMapping(value = "updateTemplate")
    public RetDto updateTemplate(CmsTemplate cmsTemplate){
        return templateClient.updateTemplate(cmsTemplate);
    }

    /**
     * 根据模板ID查询模板信息-修改模板内容用
     * @param templateId 模板ID
     * @return
     */
    @UserLoginToken
    @SystemControllerLog(type = 2,describe = "根据模板ID查询模板信息")
    @Authentication(value = "template_manage")
    @GetMapping(value = "findTemplateById")
    public void findTemplateById(String templateId, HttpServletResponse response)  throws IOException {
        String pageHtml=templateClient.findTemplateById(templateId);
        //return templateClient.findTemplateById(templateId);
        //通过response对象将内容输出
        ServletOutputStream outputStream = response.getOutputStream();
        response.setHeader("Content-type","text/html;charset=utf-8");
        outputStream.write(pageHtml.getBytes("utf-8"));
    }

    /**
     * 查询所有模板
     * @return
     */
    @UserLoginToken
    @Authentication(value = "template_manage")
    @PostMapping(value = "getAllTemplate")
    public List<CmsTemplate> getAllTemplate(){
        return templateClient.getAllTemplate();
    }

    /**
     * 创建模板
     * @param cmsTemplate
     * @return
     */
    @UserLoginToken
    @SystemControllerLog(type = 2,describe = "创建模板")
    @Authentication(value = "template_manage")
    @PostMapping(value = "createTemplate")
    public RetDto createTemplate(CmsTemplate cmsTemplate){
        return templateClient.createTemplate(cmsTemplate);
    }
}
