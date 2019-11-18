package com.eastday.demo.service;

import com.eastday.demo.dao.IMobileDao;
import com.eastday.demo.dao.IUserDao;
import com.eastday.demo.user.Mobile;
import com.eastday.demo.user.RetDto;
import com.eastday.demo.user.User;
import com.eastday.demo.utils.DateUtils;
import com.eastday.demo.utils.DesUtil;
import com.eastday.demo.utils.JwtUtils;
import com.eastday.demo.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service(value = "userService")
@Slf4j
public class UserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IMobileDao mobileDao;

    /**
     *手机验证码登录
     * @param phone
     * @param code
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public RetDto smsLogin(String phone, String code, HttpServletResponse response) {
        //验证手机号
        if(!checkPhone(phone)){
            return new RetDto(false,1,null);// 1：手机号有误
        }
        //验证短信验证码
        Mobile mobile=findUserAndCode(phone,code);
        if(mobile == null){
            return new RetDto(false,2,null);// 2:验证码有误
        }else{
            int MM =(int)DateUtils.getDistanceMinTimes(new Date(),mobile.getMobileSendTime()); // 共计分钟数*/
            System.out.println("间隔时间------"+MM+"分钟");
            if (MM > JwtUtils.EFFECTIVE_TIME) {// 时间间隔大于五分钟 验证码失效
                return new RetDto(false,3,null); //3:验证码失效
            }else if(mobile.getMobileUsable()==1){//验证码已使用
                return new RetDto(false,4,null); //4:验证码已使用
            }else{
                User user = refreshLastLoginTime(phone);
                updateMobileUsable(phone);
                Map<String,Object> map=new HashMap<>();
                map.put("userId",user.getUserId());
                map.put("accessToken",user.getAccessToken());
                //accessToken放入cookie
                Cookie cookie = new Cookie("accessToken", user.getAccessToken());
                cookie.setPath("/");
                response.addCookie(cookie);
                return new RetDto(true,0,map);
            }
        }
    }

    /**
     * 模拟发送短信验证码
     * @param phone
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public RetDto sendCode(String phone) {
        if(!checkPhone(phone)){
            return new RetDto(false,1,null);
        }else{
            //发送验证码
            String random = StringUtil.NumberCode(6);
            log.debug("验证码————"+random);
            //查询数据库手机号是否存在
            User user=findUserByPhone(phone);
            if(user!=null){
                updateMobileInfo(phone,random);
                log.debug("发送成功");
                return new RetDto(true,0,null);
            }else{
                log.debug("发送成功");
                String userId = addUser(phone);
                addMobile(userId,phone,random);
                return new RetDto(true,0,null);
            }
        }
    }

    /**
     * 验证图像验证码
     * @param
     * @return
     */
    /*public RetDto checkKaptcha(String code, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(code==null || "".equals(code)){
            return new RetDto(false,1,null);//验证码为空
        }
        if(!code.equals(session.getAttribute("rightCode"))){
            return new RetDto(false,2,null);//验证码有误
        }
        session.removeAttribute("rightCode");
        return new RetDto(true,0,null);//验证码通过
    }*/

    public User selectByPrimaryKey(String userId){
        return userDao.selectByPrimaryKey(userId);
    }

    public boolean checkPhone(String phone){
        if(StringUtil.isMobile(phone)){
            return true;
        }else{
            return false;
        }
    }

    public User findUserByPhone(String phone){
        User user=new User();
        user.setUserPhone(DesUtil.encrypt(phone));
        return userDao.selectOne(user);
    }

    public void updateMobileInfo(String phone,String code){
        Mobile mobile1=new Mobile();
        mobile1.setMobilePhone(phone);
        Mobile mobile=mobileDao.selectOne(mobile1);
        mobile.setMobileCode(code);
        mobile.setMobileSendTime(new Date());
        mobile.setMobileUsable(0);
        mobileDao.updateByPrimaryKeySelective(mobile);
    }

    public String addUser(String phone){
        User user=new User();
        user.setUserId(StringUtil.generateShortUuid());
        user.setUserPhone(DesUtil.encrypt(phone));
        user.setUserCreatTime(new Date());
        userDao.insertSelective(user);
        return user.getUserId();
    }

    public void addMobile(String userId,String phone,String code){
        Mobile mobile=new Mobile();
        mobile.setUserId(userId);
        mobile.setMobilePhone(phone);
        mobile.setMobileCode(code);
        mobile.setMobileSendTime(new Date());
        mobile.setMobileUsable(0);
        mobileDao.insertSelective(mobile);
    }

    public Mobile findUserAndCode(String phone, String code){
        Mobile mobile=new Mobile();
        mobile.setMobilePhone(phone);
        mobile.setMobileCode(code);
        return mobileDao.selectOne(mobile);
    }

    public User refreshLastLoginTime(String phone){
        User user = new User();
        user.setUserPhone(DesUtil.encrypt(phone));
        User user2 = userDao.selectOne(user);
        String accessToken=JwtUtils.getToken(user2);
        user2.setAccessToken(accessToken);
        user2.setUserLastLoginTime(new Date());
        userDao.updateByPrimaryKeySelective(user2);
        return user2;
    }

    public void updateMobileUsable(String phone){
        Mobile mobile=new Mobile();
        mobile.setMobilePhone(phone);
        Mobile mobile2=mobileDao.selectOne(mobile);
        mobile2.setMobileUsable(1);
        mobileDao.updateByPrimaryKeySelective(mobile2);
    }


}
