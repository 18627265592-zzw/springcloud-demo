package com.eastday.demo.dao;

import com.eastday.demo.news.CmsTemplate;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ITemplateDao extends Mapper<CmsTemplate>,MySqlMapper<CmsTemplate> {
}
