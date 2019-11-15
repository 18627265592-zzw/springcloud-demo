package com.eastday.demo.dao;

import com.eastday.demo.user.Menu;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface IMenuDao extends Mapper<Menu>,MySqlMapper<Menu> {

    List<Menu> selectMenuByUserId(String userId);

    List<Menu> selectMenuByRoleId(Integer roleId);

}
