package com.eastday.demo.controller;

import com.eastday.demo.client.UserClient;
import com.eastday.demo.config.SystemControllerLog;
import com.eastday.demo.service.UserService;
import com.eastday.demo.user.RetDto;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Slf4j
@RestController
@RequestMapping(value="/user")
@EnableAutoConfiguration
public class UserController {

    @Autowired
    private UserClient userClient;

    @Autowired
    private RestTemplate restTemplate;

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
    @SystemControllerLog(type = 1,describe = "用户短信登录")
    @PostMapping(value="smsLogin")
    public RetDto smsLogin(String phone,String code,HttpServletRequest request){
        RetDto retDto = userClient.smsLogin(phone,code);
        System.out.println("1");
        if(retDto.getSuccess()){
            request.setAttribute("userId",retDto.getContent().get("userId"));
        }
        return retDto;
    }


    /**
     * 模拟发送短信验证码
     * @param phone
     * @return
     */
    @PostMapping(value="sendCode")
    public RetDto sendCode(String phone){
        /*String url="";
        RetDto retDto=restTemplate.getForObject(url,RetDto.class);
        //发送成功：执行service
        //发送失败：不执行
        //返回结果*/
        return userClient.sendCode(phone);
    }


    /**
     * 验证图像验证码
     * @param code
     * @return
     */
    //@CrossOrigin(origins = {"*", "null"}) //允许跨域
    @PostMapping(value = "checkKaptcha")
    public RetDto checkKaptcha(String code,HttpServletRequest request){
        return userService.checkKaptcha(code,request);
    }

    /**
     * 生成验证码
     * @throws Exception
     */
    //@CrossOrigin(origins = {"*", "null"}) //允许跨域
    @GetMapping(value = "getKaptcha")
    public void getKaptcha (HttpServletRequest request, HttpServletResponse response) throws Exception{
        byte[] kaptchaArr = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        // 生产验证码字符串并保存到session中
        String createText = defaultKaptcha.createText();
        request.getSession().setAttribute("rightCode", createText);
        log.debug("图像验证码："+request.getSession().getAttribute("rightCode"));
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
