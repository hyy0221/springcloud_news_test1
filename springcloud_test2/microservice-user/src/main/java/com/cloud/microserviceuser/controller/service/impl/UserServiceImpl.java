package com.cloud.microserviceuser.controller.service.impl;

import com.cloud.microserviceuser.controller.service.UserService;
import com.cloud.microserviceuser.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public JdbcTemplate jdbcTemplate;
    @Override
    public User findUserById(int id) {
        String sql = "select * from t_user where id = ?";
        List<User> users = jdbcTemplate.query(sql,new Object[]{id},new BeanPropertyRowMapper<>(User.class));
        if(users.size() >= 1){
            return users.get(0);
        }
        return null;
    }

    @Override
    public User query(User user) {
        String sql = "select * from t_user where email=? and password=?";
        List<Map<String,Object>> users =  jdbcTemplate.queryForList(
                sql,
                new Object[]{user.getEmail(), user.getPassword()});
        User res = null;
        if(users.size() > 0){
            res = new User();
            Map<String,Object> kvs = users.get(0);
            res.setId(Integer.parseInt(kvs.get("id").toString()));
            res.setEmail(kvs.get("email").toString());
            res.setUsername(kvs.get("username").toString());
            res.setLevel(Integer.parseInt(kvs.get("level").toString()));
            res.setPermission(Integer.parseInt(kvs.get("permission").toString()));
            System.out.println(res);
        }
        return res;
    }

    @Override
    public boolean addUser(User user) {
        String sql = "insert into t_user(username,email,password,level,permission) values(?,?,?,?,?)";
        int row = jdbcTemplate.update(
                sql,
                new Object[]{
                        user.getUsername(),user.getEmail(),
                        user.getPassword(),user.getLevel(),
                        user.getPermission()
                });
        if(row > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean addAdminUser(User user) {
        String sql = "insert into t_user(username,email,password,level,permission) values(?,?,?,?,?)";
        int row = jdbcTemplate.update(
                sql,
                new Object[]{
                        user.getUsername(),user.getEmail(),
                        user.getPassword(),user.getLevel(),
                        100
                });
        if(row > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        String sql = "delete from t_user where id=?";
        int row = jdbcTemplate.update(sql,user.getId());
        if(row > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        String sql = "update t_user set username=?,email=?,password=? where id=?";
        int row = jdbcTemplate.update(sql,
                new Object[]{
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getId()});
        if(row > 0){
            return true;
        }
        return false;
    }

    @Override
    public List<User> allUsers() {
        String sql = "select * from t_user";
        List<User> users = jdbcTemplate.query(
                sql,
                new Object[]{},
                new BeanPropertyRowMapper<>(User.class)
        );
        return users;
    }
}
