package com.eastday.demo.user;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "user_role")
public class UserAndRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;//用户id

    @Column(name = "role_id")
    private Integer roleId;//角色id

    @Column(name = "create_time")
    private Date createTime;//创建时间
}
