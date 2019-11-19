package com.eastday.demo.user;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Data
@Table(name = "sort")
public class Sort {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer sortId;//

    @Column(name = "sort_name")
    private String sortName;//栏目名

    @Column(name = "parent_id")
    private Integer parentId;//父栏目id

    @Column(name = "lever")
    private Integer lever;//栏目级别

    @Column(name = "url")
    private String url;//地址

    @Column(name = "create_time")
    private Date createTime;//创建时间

    @Transient
    private List<Sort> childSorts;//子栏目
}
