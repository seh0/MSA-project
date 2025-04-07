package com.example.client.api.dto;

public class RedisUserInfo {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String email;
    private String role;
    private Long id;

    public RedisUserInfo() {}

    public RedisUserInfo(String email, String role,Long id){
        this.email = email;
        this.role = role;
        this.id = id;
    }
}
