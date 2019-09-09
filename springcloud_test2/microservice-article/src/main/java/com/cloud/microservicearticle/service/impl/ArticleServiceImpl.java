package com.cloud.microservicearticle.service.impl;

import com.cloud.microservicearticle.entity.Article;
import com.cloud.microservicearticle.service.ArticleService;
import com.cloud.microservicearticle.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public Article findArticleById(int id) {
        String sql = "select * from t_article where id = ?";
        List<Article> articles = jdbcTemplate.query(sql,new Object[]{id},new BeanPropertyRowMapper<>(Article.class));
        if(articles.size() >= 1){
            return articles.get(0);
        }
        return null;
    }

    @Override
    public List<Article> findArticleByAuthor(int userId) {
        //String sql1 = "select username from t_user where id=?";
        //String sql2 = "select * from t_article where author=?";

        // 根据用户id查用户表和文章表，两表联查
        String sql = "select * from t_article where author in (select username from t_user where id=?)";
        List<Article> articles = jdbcTemplate.query(
                sql,
                new Object[]{userId},
                new BeanPropertyRowMapper<>(Article.class));
        return articles;
    }

    @Override
    public boolean addArticle(Article article) {
        Timestamp timestamp = Timestamp.valueOf(article.getPublishTime());
        String sql = "insert into t_article(name,author,content,type,publish_time) values(?,?,?,?,?)";
        int row = jdbcTemplate.update(
                sql,
                new Object[]{
                        article.getName(),
                        article.getAuthor(),
                        article.getContent(),
                        article.getType(),
                        timestamp
                });
        if(row > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteArticle(Article article) {
        String sql = "delete from t_article where id=?";
        int row = jdbcTemplate.update(sql,article.getId());
        if(row > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateArticle(Article article) {
        String sql = "update t_article set `name`=? where id=?";
        int row = jdbcTemplate.update(sql,
                new Object[]{
                        article.getName(),
                        article.getId()});
        if(row > 0){
            return true;
        }
        return false;
    }

    public List<Article> getArticlesByCondition(String sql, Object[] objs){
        List<Article> articles = jdbcTemplate.query(
                sql,
                objs,
                new BeanPropertyRowMapper<>(Article.class)
        );
        // datetime -> Timestamp(Date子类)
        for(Article article : articles){
            String publishTime = article.getPublishTime();
            if(publishTime != null){
                Timestamp timestamp = Timestamp.valueOf(publishTime);
                publishTime = DateUtils.dateToString(timestamp);
                article.setPublishTime(publishTime);
            }
        }
        return articles;
    }
    @Override
    public List<Article> allArticles() {
        String sql = "select * from t_article";
        return getArticlesByCondition(sql, new Object[]{});
    }

    @Override
    public List<Article> getArticlesByType(int type) {
        String sql = "select * from t_article where type = ?";
        return getArticlesByCondition(sql, new Object[]{type});
    }
    @Override
    public List<Article> getArticlesByPage(int page){
        String sql = "select * from t_article where id between ? and ?";

        int startId = 40;
        int start = startId + (page - 1) * 10 + 1;  // 1, 11
        int end = startId + page * 10; // 10, 20

        return getArticlesByCondition(sql, new Object[]{start,end});
    }
}

