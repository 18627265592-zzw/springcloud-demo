package com.eastday.demo.dao;

import com.eastday.demo.user.Logger;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ILoggerDao extends Mapper<Logger>,MySqlMapper<Logger> {
}
