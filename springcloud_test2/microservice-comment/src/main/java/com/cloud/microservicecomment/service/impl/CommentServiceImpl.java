package com.cloud.microservicecomment.service.impl;

import com.cloud.microservicecomment.entity.Comment;
import com.cloud.microservicecomment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public Comment findCommentById(int id) {
        return null;
    }

    @Override
    public boolean addComment(Comment comment) {
        Timestamp timestamp =new Timestamp(Long.parseLong(comment.getPublishTime()));
        String sql = "insert into t_comment(author,content,up_num,down_num,publish_time,article_id) values(?,?,?,?,?,?)";
        int row = jdbcTemplate.update(sql, new Object[]{
                comment.getAuthor(),
                comment.getContent(),
                comment.getUpNum(),
                comment.getDownNum(),
                timestamp,
                comment.getArticleId()
        });

        if(row >= 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteComment(Comment comment) {
        return false;
    }

    @Override
    public boolean updateComment(Comment comment) {
        return false;
    }

    @Override
    public List<Comment> allCommentsByArticleId(int articleId) {
        String sql = "select * from t_comment where article_id=? and level=0";
        List<Comment>  comments = jdbcTemplate.query(sql, new Object[]{articleId}, new BeanPropertyRowMapper<>(Comment.class));
        return comments;
    }

    @Override
    public List<Comment> getCascadeComments(int id) {
        String sql = "select * from t_comment where reply_id=? and level=1";
        List<Comment>  comments = jdbcTemplate.query(sql,new Object[]{id},new BeanPropertyRowMapper<>(Comment.class));
        return comments;
    }
}
