package com.eastday.demo.news;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "cms_news_update")
public class CmsNewsUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "news_id")
    private String newsId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "create_time")
    private Date createTime;

}
