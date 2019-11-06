package com.eastday.demo.client;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

   /* @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;*/

    @Bean
    public BasicAuthRequestInterceptor getBasicAuthRequestInterceptor(){
        return new BasicAuthRequestInterceptor("admin", "123456");//添加认证的用户名密码
    }
}

