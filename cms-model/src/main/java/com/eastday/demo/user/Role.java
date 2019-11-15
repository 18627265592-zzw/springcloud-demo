package com.eastday.demo.user;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name = "role")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer roleId;//主键

    @Column(name = "role_name")
    private String roleName;//角色名

    /*@Sort(name = "role_code")
    private String roleCode;//角色代码*/

    @Column(name = "create_time")
    private Date createTime;//创建时间
}
