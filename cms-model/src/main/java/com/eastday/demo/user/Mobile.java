package com.eastday.demo.user;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "mobile")
public class Mobile {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String mid;

    @Column(name="uid")
    private Integer uid;

    @Column(name="mobile_phone")
    private String mobilePhone;//手机号

    @Column(name="mobile_code")
    private String mobileCode;//短信验证码

    @Column(name="mobile_send_time")
    private Date mobileSendTime;//发送时间

    @Column(name="mobile_usable")
    private Integer mobileUsable;//是否使用（0：未使用 1：已使用）

}
