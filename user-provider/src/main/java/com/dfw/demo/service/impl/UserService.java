package com.dfw.demo.service.impl;

import com.dfw.demo.dao.IUserDao;
import com.dfw.demo.entity.RetDto;
import com.dfw.demo.entity.User;
import com.dfw.demo.service.IUserService;
import com.dfw.demo.util.DesUtil;
import com.dfw.demo.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service(value = "userService")
public class UserService implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private DesUtil des;

    @Autowired
    private JwtUtils jwt;


    @Override
    public RetDto login(User user) {
        //验证手机号
        if(!jwt.isMobile(user.getPhone())){
            return new RetDto(false,"手机号有误",null);
        }

        //验证短信验证码
        User seuser=userDao.findUserAndCode(new User(des.encrypt(user.getPhone()),user.getCode()));
        if(seuser==null){
            return new RetDto(false,"验证码有误",null);
        }else{
            long endT=seuser.getDead_line();//验证码发送时间
            long startT = (new Date()).getTime();
            long ss = (startT - endT) / (1000); // 共计秒数
            int MM = (int) ss / 60; // 共计分钟数

            if (MM > jwt.EFFECTIVE_TIME) {// 时间间隔大于五分钟 验证码失效
                return new RetDto(false,"验证码失效",null);
            }else if(seuser.getUsable()==1){//验证码已使用
                return new RetDto(false,"验证码已使用",null);
            }else{
                //刷新token，更新登录时间，修改验证码状态为已使用
                String token=jwt.encode(user,7200000);//2小时
                userDao.refreshLastLoginTime(des.encrypt(user.getPhone()));
                //token过期测试
                    /*try {
                        User user2=jwt.decode(token,User.class);
                        if(user2==null){
                            System.out.println("token过期");
                        }else{
                            System.out.println("-----"+user2.getCode());
                            System.out.println("验证通过");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }*/

                return new RetDto(true,"",token);
            }
        }
    }


    @Override
    public RetDto sendCode(String phone) {
        if(!jwt.isMobile(phone)){
            return new RetDto(false,"手机号有误",null);
        }else{
            String random=jwt.NumberCode(6);
            System.out.println("验证码————"+random);
            User user2=new User();
            user2.setPhone(des.encrypt(phone));
            user2.setCode(random);
            user2.setDead_line((new Date()).getTime());
            user2.setUsable(0);
            //查询数据库手机号是否存在
            User user=userDao.findUserByPhone(des.encrypt(phone));
            if(user!=null){
                userDao.updCodeInfo(user2);
                return new RetDto(true,"发送成功",null);
            }else{
                userDao.addUser(user2);
                return new RetDto(true,"发送成功",null);
            }
        }
    }


}
