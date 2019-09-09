package com.cloud.cloudconsumer.controller;

import com.cloud.cloudconsumer.clientInterface.CommentFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    private CommentFeignClient commentFeignClient;

    @PostMapping("/comment/{id}")
    public String addComment(
            @PathVariable("id") int id,
            @RequestParam("userId") String userId,
            @RequestParam("comment_content") String content,
            Model model ) {
        Map<String, Object> res = commentFeignClient.addComment(id,userId,content);
        for(Map.Entry<String, Object> entry : res.entrySet()){
            model.addAttribute(entry.getKey(),entry.getValue());
        }
        return "redirect:/article/"+id;
    }
    @GetMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable("id") int id, HttpServletRequest request){
        String res = commentFeignClient.deleteComment(id,request);
        return res;
    }
}
