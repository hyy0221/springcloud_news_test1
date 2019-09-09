package com.cloud.cloudconsumer.clientInterface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@FeignClient(name = "microservice-article")
public interface ArticleFeignClient  {
    @GetMapping("/")
    public Map<String, Object> allArticles();
    @GetMapping("/article/{id}")
    public Map<String, Object> getArticleById(@PathVariable("id") int id);
    @PostMapping("/article/delete/{id}")
    public void deleteArticle(@PathVariable("id") int id);
    @PostMapping("/article/add")
    public boolean addArticle(HttpServletRequest request);
    @GetMapping("/articles/type/{id}")
    public Map<String, Object> getArticlesByType(@PathVariable("id") int id);
    @GetMapping("/articles/{id}/{page}")
    public Map<String, Object> getArticleByPage(@PathVariable("id") int id, @PathVariable("page") int page);
}
