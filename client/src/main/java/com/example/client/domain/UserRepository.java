package com.example.client.domain;

import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);
    User update(User user);
}
