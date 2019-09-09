package com.cloud.microservicearticle.service;

import com.cloud.microservicearticle.entity.Article;

import java.util.List;

public interface ArticleService {
    public Article findArticleById(int id);

    public List<Article> findArticleByAuthor(int userId);

    public boolean addArticle(Article article);
    public boolean deleteArticle(Article article);
    public boolean updateArticle(Article article);
    public List<Article> allArticles();

    public List<Article> getArticlesByType(int type);
    public List<Article> getArticlesByPage(int page);
}