package com.eastday.demo.dao;

import com.eastday.demo.user.Role;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface IRoleDao extends Mapper<Role>,MySqlMapper<Role> {
}
