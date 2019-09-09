package com.cloud.microservicecomment.service;


import com.cloud.microservicecomment.entity.Comment;

import java.util.List;

public interface CommentService {
    public Comment findCommentById(int id);
    public boolean addComment(Comment comment);
    public boolean deleteComment(Comment comment);
    public boolean updateComment(Comment comment);
    public List<Comment> allCommentsByArticleId(int articleId);
    public List<Comment> getCascadeComments(int id);
}