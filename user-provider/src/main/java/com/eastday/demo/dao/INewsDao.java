package com.eastday.demo.dao;

import com.eastday.demo.news.CmsNews;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface INewsDao extends Mapper<CmsNews>,MySqlMapper<CmsNews> {
    List<CmsNews> selectAllCmsNews(CmsNews cmsNews);
}
