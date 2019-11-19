package com.eastday.demo.dao;

import com.eastday.demo.user.Sort;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ISortDao extends Mapper<Sort>,MySqlMapper<Sort> {
}
