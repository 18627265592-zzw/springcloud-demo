package com.eastday.demo.controller;

import com.eastday.demo.client.UserClient;
import com.eastday.demo.service.UserService;
import com.eastday.demo.user.RetDto;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;


@RestController
@RequestMapping(value="/user")
@EnableAutoConfiguration
public class UserController {

    @Autowired
    private UserClient userClient;

    //验证码工具
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private UserService userService;

    /**
     *手机号登录
     * @param phone
     * @param code
     * @return
     */
    @PostMapping(value="smsLogin/{phone}/{code}")
    public RetDto smsLogin(@PathVariable("phone") String phone,@PathVariable(name = "code") String code){
       return userClient.smsLogin(phone,code);
    }


    /**
     * 模拟发送短信验证码
     * @param phone
     * @return
     */
    @PostMapping(value="sendCode/{phone}")
    public RetDto sendCode(@PathVariable("phone") String phone){
        return userClient.sendCode(phone);
    }


    /**
     * 验证图像验证码
     * @param code
     * @return
     */
    @GetMapping(value = "checkKaptcha/{code}")
    public RetDto checkKaptcha(@PathVariable("code") String code,HttpServletRequest request){
        return userService.checkKaptcha(code,request);
    }

    /**
     * 生成验证码
     * @throws Exception
     */
    @GetMapping(value = "getKaptcha")
    public void getKaptcha (HttpServletRequest request, HttpServletResponse response) throws Exception{
        byte[] kaptchaArr = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        // 生产验证码字符串并保存到session中
        String createText = defaultKaptcha.createText();
        request.getSession().setAttribute("rightCode", createText);
        //log.debug("图像验证码："+request.getSession().getAttribute("rightCode"));
        System.out.println("图像验证码："+request.getSession().getAttribute("rightCode"));
        // 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
        BufferedImage challenge = defaultKaptcha.createImage(createText);
        ImageIO.write(challenge, "jpg", jpegOutputStream);
        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        kaptchaArr = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(kaptchaArr);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

}
