package com.example.client.api;

import com.example.client.api.dto.LoginRequest;
import com.example.client.api.dto.RegisterRequest;
import com.example.client.api.dto.UpdateRequest;
import com.example.client.application.JwtTokenProvider;
import com.example.client.application.UserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final StringRedisTemplate redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, StringRedisTemplate redisTemplate, JwtTokenProvider jwtTokenProvider){
        this.userService = userService;
        this.redisTemplate =redisTemplate;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@ModelAttribute RegisterRequest request){
        userService.register(request.getEmail(), request.getPassword(), request.getName());
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<String> Login(@RequestBody LoginRequest request){
        String token = userService.login(request.getEmail(),request.getPassword());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization")String authHeader) {
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("토큰없음");
        }
        String token = authHeader.replace("Bearer ","");
        redisTemplate.delete(token);

        return ResponseEntity.ok("로그아웃 성공");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization")String authHeader) {
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("토큰없음");
        }
        String token = authHeader.replace("Bearer ","");
        String email = jwtTokenProvider.getEmailByToken(token);
        userService.deleteUser(email);
        redisTemplate.delete(token);

        return ResponseEntity.ok("사용자 정보 삭제");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String authHeader, @RequestBody UpdateRequest request) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("토큰없음");
        }
        String token = authHeader.replace("Bearer ", "");
        String email = jwtTokenProvider.getEmailByToken(token);
        userService.updateUser(token, email, request.getName(), request.getAddress());

        return ResponseEntity.ok("사용자 정보 업데이트 성공");
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization")String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("토큰없음");
        }
        String token = authHeader.replace("Bearer ", "");
        String email = jwtTokenProvider.getEmailByToken(token);

        return ResponseEntity.ok(userService.getUserInfo(email));
    }

}
