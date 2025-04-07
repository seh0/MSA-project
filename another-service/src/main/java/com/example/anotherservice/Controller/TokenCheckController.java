package com.example.anotherservice.Controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/check")
public class TokenCheckController {

    private final StringRedisTemplate redisTemplate;

    public TokenCheckController(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/me")
    public ResponseEntity<String> checkUser(@RequestHeader("Authorization")String authHeader){
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("토큰 누락");
        }

        String token = authHeader.replace("Bearer ","");

        String email = redisTemplate.opsForValue().get(token);

        if(email == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 만료되었거나 유효기간 지남.");
        }

        return ResponseEntity.ok(email);
    }
}
