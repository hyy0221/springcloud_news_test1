package com.cloud.cloudconsumer.controller;

import com.cloud.cloudconsumer.clientInterface.ArticleFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class ArticleController {
    @Autowired
    private ArticleFeignClient articleFeignClient;

    @GetMapping("/")
    public String allArticles(Model model){
        Map<String, Object> res = articleFeignClient.allArticles();
        for(Map.Entry<String, Object> entry : res.entrySet()){
            model.addAttribute(entry.getKey(),entry.getValue());
        }
        return "index";
    }
    @GetMapping("/article/{id}")
    public String getArticleById(@PathVariable("id") int id, Model model){
        Map<String, Object> res = articleFeignClient.getArticleById(id);
        for(Map.Entry<String, Object> entry : res.entrySet()){
            model.addAttribute(entry.getKey(),entry.getValue());
        }
        return "article_detail";
    }
    @PostMapping("/article/delete/{id}")
    public String deleteArticle(@PathVariable("id") int id, HttpServletRequest request){
        int userId = (int)request.getSession().getAttribute("userId");
        articleFeignClient.deleteArticle(id);
        return "redirect:/usercenter/"+userId;
    }
    @PostMapping("/article/add")
    public String addArticle(HttpServletRequest request, Model model){
        int userId = (int)request.getSession().getAttribute("userId");
        boolean isAdded = articleFeignClient.addArticle(request);
        if(isAdded){
            return "redirect:/usercenter/"+userId;
        }
        model.addAttribute("add_article_error","添加文章失败");
        return "redirect:/";
    }
    @GetMapping("/articles/type/{id}")
    public String getArticlesByType(@PathVariable("id") int id, Model model){
        Map<String, Object> res = articleFeignClient.getArticlesByType(id);
        for(Map.Entry<String, Object> entry : res.entrySet()){
            model.addAttribute(entry.getKey(),entry.getValue());
        }
        return "articles";
    }
    @GetMapping("/articles/{id}/{page}")
    public String getArticleByPage(@PathVariable("id") int id,
                                   @PathVariable("page") int page,
                                   Model model){
        Map<String, Object> res = articleFeignClient.getArticleByPage(id,page);
        for(Map.Entry<String, Object> entry : res.entrySet()){
            model.addAttribute(entry.getKey(),entry.getValue());
        }
        return "articles";
    }
}
