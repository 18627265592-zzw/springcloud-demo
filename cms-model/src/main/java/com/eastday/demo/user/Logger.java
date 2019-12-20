package com.eastday.demo.user;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "logger")
public class Logger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loggerId;//日志id

    @Column(name = "user_id")
    private String userId;//用户id

    @Column(name = "`describe`")
    private String describe;//日志描述

    @Column(name = "type")
    private Integer type;//日志类型（1：登录  2：其他操作）

    @Transient
    private String typestr;//日志中文

    @Column(name = "method")
    private String method;//请求方式

    @Column(name = "remoteAddr")
    private String remoteAddr;//ip地址

    @Column(name = "requestUri")
    private String requestUri;//接口地址

    @Column(name = "create_time")
    private Date createTime;//操作时间
}
