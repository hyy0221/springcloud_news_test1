package com.cloud.microservicearticle.controller;

import com.cloud.microservicearticle.entity.Article;
import com.cloud.microservicearticle.entity.ArticleType;
import com.cloud.microservicearticle.entity.Comment;
import com.cloud.microservicearticle.service.ArticleService;
import com.cloud.microservicearticle.service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class ArticleController {
    private String commentServiceName = "http://microservice-comment";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    public ArticleService articleService;
    @Autowired
    public ArticleTypeService articleTypeService;

    @GetMapping("/")
    public Map<String, Object> allArticles(){
        List<ArticleType> articleTypes = articleTypeService.allArticleTypes();
        List<String> typeNames = new ArrayList<>();
        Map<String, Object> res = new HashMap<>();
        for(ArticleType type : articleTypes){
            List<Article> articles = articleService.getArticlesByType(type.getTypeId());
            res.put(type.getTypeTitle(), articles);
            typeNames.add(type.getTypeName());
        }
        res.put("typeNames", typeNames);
        return res;
    }
    @GetMapping("/article/{id}")
    public Map<String, Object> getArticleById(@PathVariable("id") int id){
        Article article = articleService.findArticleById(id);
        Map<String, Object> res = new HashMap<>();
        res.put("article", article);
        List<Comment> comments = restTemplate.getForObject(commentServiceName + "/comments/" + id, List.class);
        res.put("comments", comments);
        res.put("comment_num", comments.size());
        return res;
    }
    @ResponseBody
    @PostMapping("/articles/author/{id}")
    public List<Article> findArticleByAuthor(@PathVariable("id") int id){
        return articleService.findArticleByAuthor(id);
    }
    @PostMapping("/article/delete/{id}")
    public void deleteArticle(@PathVariable("id") int id){
        //调接口，返回用户中心
        Article article = new Article();
        article.setId(id);
        articleService.deleteArticle(article);
    }
    @PostMapping("/article/add")
    public boolean addArticle(HttpServletRequest request){
        Article article = new Article();

        String articleName = request.getParameter("article_name");
        String author = request.getParameter("author");
        String content = request.getParameter("content");
        String type = request.getParameter("type");

        article.setName(articleName);
        article.setAuthor(author);
        article.setContent(content);
        article.setType(Integer.parseInt(type));
        article.setPublishTime("" + new Date().getTime());

        boolean isAdded = articleService.addArticle(article);
        return isAdded;
    }

    @GetMapping("/articles/type/{id}")
    public Map<String, Object> getArticlesByType(@PathVariable("id") int id){
        Map<String, Object> res = new HashMap<>();
        List<Article> articles = articleService.getArticlesByType(id);
        res.put("articles", articles);
        res.put("article_type_id", id);
        List<Integer> pages = getPages(articles);
        res.put("pages", pages);
        return res;
    }

    @GetMapping("/articles/{id}/{page}")
    public Map<String, Object> getArticleByPage(@PathVariable("id") int id,
                                   @PathVariable("page") int page){
        Map<String, Object> res = new HashMap<>();
        List<Article> articles = articleService.getArticlesByPage(page);
        res.put("articles", articles);
        res.put("article_type_id", id);
        List<Integer> pages = getPages(articles);
        res.put("pages", pages);
        return res;
    }

    private List<Integer> getPages(List<Article> articles){
        List<Integer> pages = new ArrayList<>();
        for(int i=0;i <= (articles.size()-1)/10; i++){
            pages.add(i+1);
        }
        return pages;
    }
}
