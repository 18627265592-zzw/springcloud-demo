package com.eastday.demo.dao;

import com.eastday.demo.news.CmsTemplateUpdate;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ITemplateUpdateDao extends Mapper<CmsTemplateUpdate>,MySqlMapper<CmsTemplateUpdate> {
}
