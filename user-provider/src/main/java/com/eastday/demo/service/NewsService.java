package com.eastday.demo.service;

import com.eastday.demo.dao.INewsDao;
import com.eastday.demo.dao.INewsUpdateDao;
import com.eastday.demo.keys.ConstantKey;
import com.eastday.demo.news.CmsNews;
import com.eastday.demo.news.CmsNewsUpdate;
import com.eastday.demo.user.RetDto;
import com.eastday.demo.utils.CmsUtils;
import com.eastday.demo.utils.JwtUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "newsService")
public class NewsService {

    @Autowired
    INewsDao newsDao;

    @Autowired
    INewsUpdateDao newsUpdateDao;

    public RetDto addNews(CmsNews cmsNews){
        try {
            String publishUserId = JwtUtils.getTokenUserId();
            if(cmsNews != null && StringUtils.isNotBlank(publishUserId)){
                String newsId = CmsUtils.generateShortUuid();
                cmsNews.setNewsId(newsId);
                cmsNews.setPublishUserId(publishUserId);
                cmsNews.setLastUpdateTime(new Date());
                cmsNews.setCreateTime(new Date());
                newsDao.insertSelective(cmsNews);
                return new RetDto(true, 0, null);// 0：操作成功
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new RetDto(false, 1, null);// 1：操作失败
    }

    public Map<String,Object> getAllNews(CmsNews cmsNews,Integer page,Integer rows){
        Map<String,Object> map = new HashMap<>();
       /* Example example = new Example(cmsNews.getClass());
        Example.Criteria criteria = example.createCriteria();
        if(cmsNews.getState() != 9){  //先默认9为“全部”
            criteria.andEqualTo("state",cmsNews.getState());
        }
        if(StringUtils.isNotBlank(cmsNews.getNewsTitle())){
            criteria.andLike("newsTitle","%"+cmsNews.getNewsTitle()+"%");
        }*/
        PageHelper.startPage(page,rows);
        List<CmsNews> list = newsDao.selectAllCmsNews(cmsNews);
        for(CmsNews li:list){
            if(li.getState()==0){
                li.setStatestr("未审核");
            }else if(li.getState()==1){
                li.setStatestr("待审核");
            }else if(li.getState()==2){
                li.setStatestr("审核通过");
            }else if(li.getState()==3){
                li.setStatestr("审核未通过");
            }else if(li.getState()==4){
                li.setStatestr("已删除");
            }
        }
        PageInfo<CmsNews> pageInfo = new PageInfo<>(list);
        map.put("total",pageInfo.getTotal());//总记录数
        map.put("rows",pageInfo.getList());//结果集
        return map;
    }

    public RetDto checkNews(String newsId,String str){
        String checkUserId = JwtUtils.getTokenUserId();
        if(StringUtils.isNotBlank(newsId) && StringUtils.isNotBlank(str) && StringUtils.isNotBlank(checkUserId)){
            CmsNews cmsNews = newsDao.selectByPrimaryKey(newsId);
            if(cmsNews != null){
                CmsNews cmsNews1 = new CmsNews();
                if("publish".equals(str) || "agree".equals(str) || "disagree".equals(str) || "delete".equals(str)){
                    if("publish".equals(str)){  //发布
                       String newsUrl = createPage(newsId);//生成html页面并上传
                        if("false".equals(newsUrl)){
                            return new RetDto(false, 2, null);// 2：上传文件失败
                        }else{
                            cmsNews1.setState(1);
                            cmsNews1.setNewsUrl(newsUrl);
                        }
                    }else if("agree".equals(str)){  //审核通过
                        cmsNews1.setState(2);
                        cmsNews1.setCheckUserId(checkUserId);
                    }else if("disagree".equals(str)){  //审核不通过
                        cmsNews1.setState(3);
                        cmsNews1.setCheckUserId(checkUserId);
                    }else if("delete".equals(str)){  //删除
                        cmsNews1.setState(4);
                    }
                    cmsNews1.setNewsId(newsId);
                    cmsNews1.setStateUpdateTime(new Date());
                    newsDao.updateByPrimaryKeySelective(cmsNews1);
                    return new RetDto(true, 0, null);// 0：操作成功
                }
            }
        }
        return new RetDto(false, 1, null);// 1：操作失败
    }

    public String previewNews(String newsId){
        try {
            CmsNews cmsNews = newsDao.selectByPrimaryKey(newsId);
            if(cmsNews != null){
                //创建配置类
                Configuration configuration = new Configuration(Configuration.getVersion());
                //设置模板路径
                configuration.setDirectoryForTemplateLoading(new File(ConstantKey.TEMPLATE_FILE_VISIT_PATH));
                //设置字符集
                configuration.setDefaultEncoding("utf-8");
                //加载模板
                Template template = configuration.getTemplate(cmsNews.getTemplateId() + ".ftl");
                //数据模型
                Map<String,Object> map = getMap(cmsNews);
                //静态化
                String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
                return content;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Map<String,Object> getMap(CmsNews cmsNews){
        Map<String,Object> map = new HashMap<>();
        map.put("news",cmsNews);
        return map;
    }

    public RetDto updateNews(CmsNews cmsNews){
        try {
            String userId = JwtUtils.getTokenUserId();
            if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(cmsNews.getNewsId())){
                CmsNews cmsNews1 = newsDao.selectByPrimaryKey(cmsNews.getNewsId());
                if(cmsNews1 != null){
                    cmsNews.setState(0);//修改后进入未审核状态，需要重新提交审核
                    cmsNews.setLastUpdateTime(new Date());
                    newsDao.updateByPrimaryKeySelective(cmsNews);
                    CmsNewsUpdate cmsNewsUpdate = new CmsNewsUpdate();
                    cmsNewsUpdate.setNewsId(cmsNews.getNewsId());
                    cmsNewsUpdate.setUserId(userId);
                    cmsNewsUpdate.setCreateTime(new Date());
                    newsUpdateDao.insertSelective(cmsNewsUpdate);
                    return new RetDto(true, 0, null);// 0：成功
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new RetDto(false, 1, null);// 1：修改失败;
    }

    //生成新闻页面
    public String createPage(String newsId){
        try {
            String content = previewNews(newsId);//生成新闻页面内容
            if(StringUtils.isNotBlank(content)){
                InputStream inputStream = IOUtils.toInputStream(content);
                //输出文件
                FileOutputStream fileOutputStream = new FileOutputStream(new File(ConstantKey.PAGE_FILE_PATH+newsId+".html"));
                IOUtils.copy(inputStream, fileOutputStream);
                inputStream.close();
                fileOutputStream.close();
                return ConstantKey.PAGE_FILE_PATH+newsId+".html";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "false";
    }

}
