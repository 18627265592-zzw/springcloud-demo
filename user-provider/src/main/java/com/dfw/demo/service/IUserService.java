package com.dfw.demo.service;

import com.dfw.demo.entity.RetDto;
import com.dfw.demo.entity.User;

public interface IUserService {


    /**
     *手机号登录
     * @param user
     * @return
     */
    public RetDto login(User user);

    /**
     * 模拟发送短信验证码
     * @param phone
     * @return
     */
    public RetDto sendCode(String phone);

}
