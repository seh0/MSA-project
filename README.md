# MSA + Redis 기반 인증 시스템

## 📌 프로젝트 구조 (Domain-Driven MSA)

```
msa-auth-project/
├── client (user-service)
├── another-service (JWT 통화 가능한 기본 서비스)
├── gateway (Spring Cloud Gateway)
└── redis (Docker 컨테이너 or 원기 연결)
```

---

## 🚀 모험

- JWT 가능 및 Redis에 사용자 정보 저장
- 다른 서비스들이 해당 JWT 통화로 사용자 정보 검사 가능

---

## ✅ 전체 목표

### 1. 로그인 (client-service)
- 이메일 + 비밀번호 검사 후 JWT 발급
- 도츠르에 JWT (key) / 사용자 정보 (value, JSON) 저장

```json
{
  "email": "aaa123@gmail.com",
  "role": "USER",
  "id": 42
}
```

---

### 2. Redis 저장 구조

#### 키
- JWT 통화 문자열

#### 번개 (value: JSON 문자열)
```json
{
  "email": "사용자 이메일",
  "role": "USER or ADMIN",
  "id": 사용자 ID
}
```

#### TTL 값
- JWT 만료 시 포함된 만료 시간 (30분 등)

---

### 3. Redis 저장 로직 (UserService)

```java
RedisUserInfo redisUserInfo = new RedisUserInfo(user.getEmail(), user.getRole(), user.getId());
String redisValue = objectMapper.writeValueAsString(redisUserInfo);

redisTemplate.opsForValue().set(
    token,
    redisValue,
    jwtTokenProvider.getExpiration(),
    TimeUnit.MILLISECONDS
);
```

---

### 4. 다른 서비스에서 검사 (another-service)

```java
@GetMapping("/api/check/me")
public ResponseEntity<?> checkUser(@RequestHeader("Authorization") String authHeader) {
    if (!authHeader.startsWith("Bearer ")) return ResponseEntity.badRequest().body("토큰 누락");

    String token = authHeader.replace("Bearer ", "");
    String json = redisTemplate.opsForValue().get(token);

    if (json == null) return ResponseEntity.status(401).body("토큰 만료되어있지 않음");

    RedisUserInfo userInfo = new ObjectMapper().readValue(json, RedisUserInfo.class);
    return ResponseEntity.ok(userInfo);
}
```

---

## 📂 Redis 예시

```bash
127.0.0.1:6379> keys *
1) "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJh..."

127.0.0.1:6379> get eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJh...
"{\"email\":\"aaa123@gmail.com\",\"role\":\"USER\",\"id\":42}"
```

---

## 편의 확장 가능성

| 기능 | 설명 |
|--------|------|
| 회원가입/로그인 | JWT 발급 + Redis 저장 |
| 로그아웃 | Redis에서 del(token) |
| ADMIN 권한 | role: "ADMIN" 검사로 관리 |
| 링크와이드 | Refresh Token 포함된 구조 변환 가능 |
| MSA 가용성 | 사용자 정보가 모든 서비스에서 통화되면 관리가 종종해진다 |

---

## 🚜 사용 기술

- Spring Boot 3
- Spring Security 6
- JWT (jjwt)
- Redis (Spring Data Redis)
- Spring Cloud Gateway
- Docker

---

## 후가 안전 / 확장 가능 정책

- Refresh Token + Access Token 변환
- 로그아웃 시 Redis Blacklist 가능
- 서비스 간 사용자 정보 공유 시 role 기반 인가 가능
- 프로필/박스 등급에서 userId 기반 관리

---

이 구조는 실용 MSA 토큰 인증/인가 패턴을 보조한 구조이며,
아래에서 업그레이드하게 가능한 점과 다른 서비스들과의 통화성을 높이기 위해 설계되었습니다.

