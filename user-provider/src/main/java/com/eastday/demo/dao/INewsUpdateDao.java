package com.eastday.demo.dao;

import com.eastday.demo.news.CmsNewsUpdate;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface INewsUpdateDao extends Mapper<CmsNewsUpdate>,MySqlMapper<CmsNewsUpdate> {
}
