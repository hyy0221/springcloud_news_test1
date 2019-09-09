package com.cloud.cloudconsumer.entity;

public class User {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private Integer level;
    private Integer permission;

    public User() {
    }

    public User(String username, String email, String password, Integer level, Integer permission) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.level = level;
        this.permission = permission;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", level=" + level +
                ", permission=" + permission +
                '}';
    }
}
