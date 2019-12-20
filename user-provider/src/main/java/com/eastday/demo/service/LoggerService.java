package com.eastday.demo.service;

import com.eastday.demo.dao.ILoggerDao;
import com.eastday.demo.user.Logger;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "loggerService")
public class LoggerService {

    @Autowired
    private ILoggerDao loggerDao;

    public void addLogger(Logger logger){
        loggerDao.insertSelective(logger);
    }

    public Map<String,Object> findAllLogger(Integer page, Integer rows){
        Map<String,Object> map = new HashMap<>();
        Example example = new Example(Logger.class);
        Example.Criteria criteria = example.createCriteria();
        /*if(StringUtils.isNotBlank(logger.getUserId())){
            criteria.andEqualTo("userId",logger.getUserId());
            criteria.andLike("userId","%"+logger.getUserId()+"%");//模糊查询
        }*/
        PageHelper.startPage(page,rows,"create_time desc");
        List<Logger> list=loggerDao.selectByExample(example);
        for(Logger li:list){
            if(li.getType() == 1){
                li.setTypestr("登录");
            }else if(li.getType() == 2){
                li.setTypestr("其他操作");
            }
        }
        PageInfo<Logger> pageInfo = new PageInfo<>(list);
        map.put("total",pageInfo.getTotal());//总记录数
        map.put("rows",pageInfo.getList());//结果集
        return map;
    }
}
