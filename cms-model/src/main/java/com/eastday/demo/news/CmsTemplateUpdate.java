package com.eastday.demo.news;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "cms_template_update")
public class CmsTemplateUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "template_id")
    private String templateId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "create_time")
    private Date createTime;
}
