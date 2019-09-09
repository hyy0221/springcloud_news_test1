package com.cloud.microservicecomment.controller;

import com.cloud.microservicecomment.entity.Comment;
import com.cloud.microservicecomment.entity.User;
import com.cloud.microservicecomment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private RestTemplate restTemplate;
    private String userServiceName = "http://microservice-user";

    @GetMapping("comments/{id}")
    public List<Comment> allComment(@PathVariable("id") int id){
        return commentService.allCommentsByArticleId(id);
    }
    @PostMapping("/comment/{id}")
    public Map<String, Object> addComment(
            @PathVariable("id") int id,
            @RequestParam("userId") String userId,
            @RequestParam("comment_content") String content) {
        Map<String, Object > res = new HashMap<>();
        User user = restTemplate.getForObject(userServiceName + "/user/" + userId, User.class);
        Comment comment  = new Comment();
        comment.setArticleId(id);
        if(user != null){
            comment.setAuthor(user.getUsername());
        }else {
            comment.setAuthor("");
        }
        comment.setContent(content);
        comment.setDownNum(0);
        comment.setUpNum(0);
        comment.setLevel(0);
        comment.setPublishTime(new Date().getTime()+""); // 时间可以转成long， 毫秒数，当前的时间-1970.1.1

        boolean isAdded = commentService.addComment(comment);
        if(!isAdded){
            res.put("error", "添加失败");
        }
        return res;
    }
    @ResponseBody
    @GetMapping("/comment/cascade/{id}")
    public List<Comment> getCascadeComments(@PathVariable("id") int id){
        List<Comment> comments = commentService.getCascadeComments(id);
        return comments;
    }
    @GetMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable("id") int id, HttpServletRequest request){
        int userId = (int)request.getSession().getAttribute("userId");
        Comment comment = new Comment();
        comment.setId(id);
        commentService.deleteComment(comment);
        return "redirect:/usercenter/"+userId;
    }
}
