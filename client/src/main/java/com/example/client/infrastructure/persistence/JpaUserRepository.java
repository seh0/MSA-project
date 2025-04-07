package com.example.client.infrastructure.persistence;

import com.example.client.domain.User;
import com.example.client.domain.UserRepository;
import com.example.client.infrastructure.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository {
    private final SpringDataUserRepository repository;

    public JpaUserRepository(SpringDataUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user){
        UserEntity savedEntity = repository.save(UserEntity.fromDomain(user));
        return savedEntity.toDomain();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(UserEntity::toDomain);
    }

    @Override
    public void deleteByEmail(String email) {
        repository.deleteByEmail(email);
    }

    @Override
    public User update(User user) {
        Optional<UserEntity> existingEntityOpt = repository.findByEmail(user.getEmail());
        if (existingEntityOpt.isPresent()) {
            UserEntity existingEntity = existingEntityOpt.get();
            existingEntity.updateFromDomain(user);

            UserEntity updatedEntity = repository.save(existingEntity);
            return updatedEntity.toDomain();
        } else {
            throw new IllegalArgumentException("이메일 " + user.getEmail() + "에 해당하는 사용자가 존재하지 않습니다.");
        }
    }

}
