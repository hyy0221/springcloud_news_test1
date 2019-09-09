package com.cloud.cloudconsumer.entity;
import java.util.List;

/**
 * Created by shenwudi on 2019/4/22.
 */
public class Article {
    private Integer id;
    private String name;
    private String author;
    private Integer type;
    private List<String> keywords;
    private String content;
    private String publishTime;

    public Article() {
    }

    public Article(String name, String author, Integer type) {
        this.name = name;
        this.author = author;
        this.type = type;
    }

    public Article(Integer id, String name, String author, Integer type, List<String> keywords, String content, String publishTime) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.type = type;
        this.keywords = keywords;
        this.content = content;
        this.publishTime = publishTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
