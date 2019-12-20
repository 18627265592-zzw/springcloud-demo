package com.eastday.demo.news;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "cms_template")
public class CmsTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String templateId;//模板id

    @Column(name = "template_name")
    private String templateName;//模板名称

    @Column(name = "template_file_url")
    private String templateFileUrl;//模板文件url

    @Column(name = "state")
    private Integer state;//状态（1：存在  2：已删除）

    @Transient
    private String statestr;//状态中文

    @Column(name = "create_user_id")
    private String createUserId;//创建人用户id

    @Column(name = "create_time")
    private Date createTime;//创建时间

    @Transient
    private String templateContent;//模板内容

}
