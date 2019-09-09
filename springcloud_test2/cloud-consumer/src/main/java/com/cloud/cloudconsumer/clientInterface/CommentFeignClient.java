package com.cloud.cloudconsumer.clientInterface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@FeignClient(name = "microservice-comment")
public interface CommentFeignClient  {
    @PostMapping("/comment/{id}")
    public Map<String, Object> addComment(
            @PathVariable("id") int id,
            @RequestParam("userId") String userId,
            @RequestParam("comment_content") String content);
    @GetMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable("id") int id, HttpServletRequest request);
}
