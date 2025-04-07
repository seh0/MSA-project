package com.example.client.infrastructure;

import com.example.client.domain.User;
import jakarta.persistence.*;

import javax.management.relation.Role;
import java.time.LocalDateTime;

@Entity
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt= LocalDateTime.now(); //트랜잭션 실행이전에 초기화
    }

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        USER,ADMIN
    }

    @Column
    private String address;

    //정적 팩토리 domain -> entity
    public static UserEntity fromDomain(User user){
        UserEntity e = new UserEntity();
        e.email = user.getEmail();
        e.password = user.getPassword();
        e.name = user.getName();
        e.createdAt = user.getCreatedAt();
        e.role = Role.valueOf(user.getRole());
        e.address = user.getAddress();
        return e;
    }

    // domain <- entity
    public User toDomain(){
        User user = new User(email, password, name,
                role != null ? role.name() : null,
                address,
                createdAt);
        user.setId(this.id);
        return user;
    }

    public void updateFromDomain(User user) {
        this.name = user.getName();
        this.address = user.getAddress();
    }

    //기본 생성자
    protected UserEntity() {}
}
