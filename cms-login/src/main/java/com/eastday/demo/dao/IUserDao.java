package com.eastday.demo.dao;

import com.eastday.demo.user.User;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface IUserDao extends Mapper<User>,MySqlMapper<User> {


}
