package com.example.client.infrastructure.persistence;

import com.example.client.infrastructure.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//JPA용 저장소 인터페이스
public interface SpringDataUserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String email);
    void deleteByEmail(String email);
}
