package com.b3qTest.pojo;


import java.util.Date;

/**
 * @author b3q
 * news JAVA Bean
 */
public class News {

    public News() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private  int id;
    // 标题
    private String title;

    // 内容
    private String content;

    // 作者
    private String author;

    // 日期
    private Date date;

    // 所属栏目
    private String article_column;

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getBriefImg() {
        return briefImg;
    }

    public void setBriefImg(String briefImg) {
        this.briefImg = briefImg;
    }

    // 新闻简介
    private String brief;

    // 新闻简介缩略图
    private String briefImg;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getArticle_column() {
        return article_column;
    }

    public void setArticle_column(String article_column) {
        this.article_column = article_column;
    }
}
