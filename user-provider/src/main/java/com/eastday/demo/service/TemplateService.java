package com.eastday.demo.service;

import com.eastday.demo.dao.ITemplateDao;
import com.eastday.demo.dao.ITemplateUpdateDao;
import com.eastday.demo.keys.ConstantKey;
import com.eastday.demo.news.CmsNews;
import com.eastday.demo.news.CmsTemplate;
import com.eastday.demo.news.CmsTemplateUpdate;
import com.eastday.demo.user.RetDto;
import com.eastday.demo.utils.CmsUtils;
import com.eastday.demo.utils.JwtUtils;
import com.eastday.demo.utils.UploadUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "templateService")
public class TemplateService {

    @Autowired
    private ITemplateDao templateDao;

    @Autowired
    ITemplateUpdateDao templateUpdateDao;




    public RetDto addTemplate(MultipartFile file, String templateName){
        RetDto retDto=new RetDto(true, 0, null);// 0：成功
        try {
            CmsTemplate cmsTemplate1 = new CmsTemplate();
            cmsTemplate1.setTemplateName(templateName);
            CmsTemplate cmsTemplate2 = templateDao.selectOne(cmsTemplate1);
            if(cmsTemplate2 != null){
                retDto=new RetDto(false, 3, null);// 3：模板名称重复
            }else {
                String templateId= CmsUtils.generateShortUuid();//模板id
                String filepath = ConstantKey.TEMPLATE_FILE_PATH;//上传地址
                String visitUrl = ConstantKey.TEMPLATE_FILE_VISIT_PATH;//访问地址
                String fileName=templateId+".ftl";//文件名称
                String str = UploadUtils.uploadAll(filepath,fileName,visitUrl,file);//访问url(上传失败返回false,成功返回访问地址)
                if("false".equals(str)){
                    retDto=new RetDto(false, 1, null);// 1：上传失败
                }else{
                    String createUserId=JwtUtils.getTokenUserId();
                    CmsTemplate cmsTemplate = new CmsTemplate();
                    cmsTemplate.setTemplateId(templateId);
                    cmsTemplate.setTemplateName(templateName);
                    cmsTemplate.setTemplateFileUrl(str);
                    cmsTemplate.setCreateUserId(createUserId);
                    cmsTemplate.setCreateTime(new Date());
                    templateDao.insertSelective(cmsTemplate);
                }
            }
        }catch (Exception e){
            retDto=new RetDto(false, 2, null);// 2：操作失败
            e.printStackTrace();
        }
        return retDto;
    }


    public RetDto testTemplate(String templateId){
        try {
            //创建配置类
            Configuration configuration = new Configuration(Configuration.getVersion());
            //设置模板路径
            configuration.setDirectoryForTemplateLoading(new File(ConstantKey.TEMPLATE_FILE_VISIT_PATH));
            //设置字符集
            configuration.setDefaultEncoding("utf-8");
            //加载模板
            Template template = configuration.getTemplate(templateId + ".ftl");
            System.out.println("-------"+template.toString());
            //数据模型
            Map<String,Object> map = getMap();
            //静态化
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
            InputStream inputStream = IOUtils.toInputStream(content);
            //输出文件
            FileOutputStream fileOutputStream = new FileOutputStream(new File(ConstantKey.PAGE_FILE_PATH+templateId+".html"));
            IOUtils.copy(inputStream, fileOutputStream);
            inputStream.close();
            fileOutputStream.close();
            return new RetDto(true, 0, null);// 0：成功
        }catch (Exception e){
            e.printStackTrace();
        }
        return new RetDto(false, 1, null);// 1：操作失败;
    }

    public Map<String,Object> getMap(){
        Map<String, Object> map = new HashMap<>();
        CmsNews cmsNews = new CmsNews();
        cmsNews.setNewsTitle("高以翔猝死");
        cmsNews.setNewsContent("11月27日在《追我吧》第九期节目的录制过程中，当期参与嘉宾高以翔跑时突然减速倒地，节目现场医护人员第一时间展开救治，并紧急将其送往医院。经过两个多小时的全力抢救，医院最终宣布高以翔心源性猝死。我们感到无比痛心和万分悲伤！ 意外发生后，节目团队及经纪团队一直守护在他身边，节目组正协同其经纪团队联系其家人共同妥善处理善后事宜。");
        cmsNews.setLastUpdateTime(new Date());
        cmsNews.setNewsAuthor("虾米");
        map.put("news",cmsNews);
        return map;
    }

    public Map<String,Object> findAllTemplate(CmsTemplate cmsTemplate,Integer page,Integer rows){
        Map<String,Object> map = new HashMap();
        Example example = new Example(CmsTemplate.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(cmsTemplate.getTemplateName())){
            criteria.andLike("templateName","%"+cmsTemplate.getTemplateName()+"%");
        }
        if(StringUtils.isNotBlank(cmsTemplate.getTemplateId())){
            criteria.andLike("templateId","%"+cmsTemplate.getTemplateId()+"%");
        }
        if(cmsTemplate.getState() != 0){
            criteria.andEqualTo("state",cmsTemplate.getState());
        }
        PageHelper.startPage(page,rows,"create_time desc");
        List<CmsTemplate> list = templateDao.selectByExample(example);
        for(CmsTemplate li:list){
            if(li.getState() == 1){
                li.setStatestr("存在");
            }else if(li.getState() == 2){
                li.setStatestr("已删除");
            }
        }
        PageInfo<CmsTemplate> pageInfo = new PageInfo<>(list);
        map.put("total",pageInfo.getTotal());//总记录数
        map.put("rows",pageInfo.getList());//结果集
        return map;
    }

    public RetDto deleteTemplate(String templateId){
        try {
            if(StringUtils.isNotBlank(templateId)){
                CmsTemplate cmsTemplate = templateDao.selectByPrimaryKey(templateId);
                if(cmsTemplate != null){
                    CmsTemplate cmsTemplate1 = new CmsTemplate();
                    cmsTemplate1.setTemplateId(templateId);
                    cmsTemplate1.setState(2);
                    int i = templateDao.updateByPrimaryKeySelective(cmsTemplate1);
                    if(i == 1){
                        return new RetDto(true, 0, null);// 0：成功
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new RetDto(false, 1, null);// 1：删除失败
    }

    public RetDto updateTemplate(CmsTemplate cmsTemplate){
        try {
            String userId = JwtUtils.getTokenUserId();
            if(StringUtils.isNotBlank(cmsTemplate.getTemplateId()) && StringUtils.isNotBlank(userId)){
                CmsTemplate cmsTemplate1 = templateDao.selectByPrimaryKey(cmsTemplate.getTemplateId());
                if(cmsTemplate1 != null){
                    //修改模板数据
                    templateDao.updateByPrimaryKeySelective(cmsTemplate);
                    CmsTemplateUpdate cmsTemplateUpdate = new CmsTemplateUpdate();
                    cmsTemplateUpdate.setUserId(userId);
                    cmsTemplateUpdate.setTemplateId(cmsTemplate.getTemplateId());
                    cmsTemplateUpdate.setCreateTime(new Date());
                    //添加修改记录
                    templateUpdateDao.insertSelective(cmsTemplateUpdate);
                    //修改文件
                    if(StringUtils.isNotBlank(cmsTemplate.getTemplateContent())){
                        String templateFile = createTemplateFile(cmsTemplate.getTemplateId(), cmsTemplate.getTemplateContent());
                        if("false".equals(templateFile)){
                            return new RetDto(false, 2, null);// 2：修改内容失败
                        }
                    }
                    return new RetDto(true, 0, null);// 0：成功
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new RetDto(false, 1, null);// 1：修改失败
    }


    public String createTemplateFile(String templateId, String templateContent){
        try {
            if(StringUtils.isNotBlank(templateContent)){
                InputStream inputStream = IOUtils.toInputStream(templateContent);
                //输出文件
                FileOutputStream fileOutputStream = new FileOutputStream(new File(ConstantKey.TEMPLATE_FILE_VISIT_PATH+templateId+".ftl"));
                IOUtils.copy(inputStream, fileOutputStream);
                inputStream.close();
                fileOutputStream.close();
                return ConstantKey.TEMPLATE_FILE_VISIT_PATH+templateId+".ftl";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "false";
    }

    public String findTemplateById(String templateId){
        try {
            if(StringUtils.isNotBlank(templateId)){
                CmsTemplate cmsTemplate = templateDao.selectByPrimaryKey(templateId);
                //读取模板文件
                String url=ConstantKey.TEMPLATE_FILE_VISIT_PATH+templateId+".ftl";
                BufferedReader br = new BufferedReader(new FileReader(url));
                String str = "";
                StringBuffer sb = new StringBuffer();
                while ((str = br.readLine()) != null) {
                    sb.append(str).append("\r\n");
                }
                br.close();
               /* cmsTemplate.setTemplateContent(new String(sb));
                return cmsTemplate;*/
               return new String(sb);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<CmsTemplate> getAllTemplate(){
        return templateDao.selectAll();
    }

}
