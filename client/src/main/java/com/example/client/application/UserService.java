package com.example.client.application;

import com.example.client.api.dto.RedisUserInfo;
import com.example.client.domain.User;
import com.example.client.domain.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate redisTemplate;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, StringRedisTemplate redisTemplate) {
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisTemplate = redisTemplate;
    }

    public void register(String email , String password, String name){
        userRepository.findByEmail(email).ifPresent(user-> {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        });

        User user = User.createUser(email,password,name ,passwordEncoder); //팩토리 정적 메서드로 저장
        userRepository.save(user);
    }

    public String login(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재치 앖는 사용자입니다."));

        if(!passwordEncoder.matches(password , user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(user.getEmail());

        RedisUserInfo redisUserInfo = new RedisUserInfo(
                user.getEmail(),
                user.getRole(),
                user.getId()
        );

        try {
            String redisvalue = objectMapper.writeValueAsString(redisUserInfo); //json직렬화과정

            redisTemplate.opsForValue().set(
                    token,
                    redisvalue,
                    jwtTokenProvider.getExpiration(),
                    TimeUnit.MILLISECONDS
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return token;
    }

    @Transactional
    public void deleteUser(String email){
        if (userRepository.findByEmail(email).isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }
        userRepository.deleteByEmail(email);
    }

    @Transactional
    public void updateUser(String token, String email, String newName, String newAddress) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        user.setName(newName);
        user.setAddress(newAddress);

        userRepository.update(user);

        RedisUserInfo redisUserInfo = new RedisUserInfo(
                user.getEmail(),
                user.getRole(),
                user.getId()
        );

        try {
            String redisvalue = objectMapper.writeValueAsString(redisUserInfo);
            redisTemplate.opsForValue().set(
                    token,
                    redisvalue,
                    jwtTokenProvider.getExpiration(),
                    TimeUnit.MILLISECONDS
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserInfo(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return user;
    }
}
