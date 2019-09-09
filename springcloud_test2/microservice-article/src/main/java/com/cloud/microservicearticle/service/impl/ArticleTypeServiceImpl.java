package com.cloud.microservicearticle.service.impl;

import com.cloud.microservicearticle.entity.ArticleType;
import com.cloud.microservicearticle.service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTypeServiceImpl implements ArticleTypeService {
    @Autowired
    public JdbcTemplate jdbcTemplate;
    @Override
    public List<ArticleType> allArticleTypes() {
        String sql = "select * from t_article_type LIMIT 8";
        List<ArticleType> articleTypes= jdbcTemplate.query(
                sql,
                new Object[]{},
                new BeanPropertyRowMapper<>(ArticleType.class)
        );
        return articleTypes;
    }
}
