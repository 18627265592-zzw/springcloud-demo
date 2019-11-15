package com.eastday.demo.service;

import com.eastday.demo.user.RetDto;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service(value = "userService")
public class UserService {

    /**
     * 验证图像验证码
     * @param code
     * @return
     */
    public RetDto checkKaptcha( String code, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(code==null || "".equals(code)){
            return new RetDto(false,1,null);//验证码为空
        }
        if(!code.equals(session.getAttribute("rightCode"))){
            return new RetDto(false,2,null);//验证码有误
        }
        session.removeAttribute("rightCode");
        //验证码正确，发送短信

        //模拟发送短信

        return new RetDto(true,0,null);//验证码通过
    }
}
