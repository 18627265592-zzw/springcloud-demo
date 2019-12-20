package com.eastday.demo.news;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "cms_news")
public class CmsNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String newsId;//新闻id

    @Column(name = "sort_id")
    private Integer sortId;//栏目id

    @Transient
    private String sortName;//栏目名

    @Column(name = "template_id")
    private String templateId;//模板id

    @Transient
    private String templateName;//模板名称

    @Column(name = "news_title")
    private String newsTitle;//新闻标题

    @Column(name = "news_content")
    private String newsContent;//新闻内容

    @Column(name = "news_author")
    private String newsAuthor;//新闻编辑人

    @Column(name = "publish_user_id")
    private String publishUserId;//创建新闻用户id

    @Column(name = "check_user_id")
    private String checkUserId;//审核新闻用户id

    @Column(name = "state")
    private Integer state;//状态（0：未审核 1：待审核 2：审核通过 3：审核失败 4：已删除）

    @Transient
    private String statestr;//状态中文

    @Column(name = "news_url")
    private String newsUrl;//页面存放地址

    @Column(name = "state_update_time")
    private Date stateUpdateTime;//状态更新时间

    @Column(name = "last_update_time")
    private Date lastUpdateTime;//最后修改时间

    @Column(name = "create_time")
    private Date createTime;//创建时间

}
