package com.example.client.domain;

import jakarta.persistence.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;


public class User {

    public User(String email, String password, String name, String role, String address, LocalDateTime createdAt) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.address = address;
        this.createdAt = createdAt;
    }

    private Long id;

    private String email;

    private String password;

    private String name;

    public String getRole() {
        return role;
    }

    public String getAddress() {
        return address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    private String role;

    private String address;

    private LocalDateTime createdAt;


    public boolean isPasswordMatch(String raw) {
        return this.password.equals(raw);
    }

    public String getEmail(){
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public static User createUser(String email, String rawPassword, String name, PasswordEncoder encoder){
        String encodedPassword = encoder.encode(rawPassword);
        String role = email.startsWith("admin") ? "ADMIN" : "USER";

        return new User(
                email,
                encodedPassword,
                name,
                role
                ,null
                ,LocalDateTime.now()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
