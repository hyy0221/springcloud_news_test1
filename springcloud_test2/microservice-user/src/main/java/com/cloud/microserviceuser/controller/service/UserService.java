package com.cloud.microserviceuser.controller.service;

import com.cloud.microserviceuser.entity.User;

import java.util.List;

public interface UserService {
    public User findUserById(int id);
    public User query(User user);
    public boolean addUser(User user);
    public boolean addAdminUser(User user);
    public boolean deleteUser(User user);
    public boolean updateUser(User user);
    public List<User> allUsers();
}
