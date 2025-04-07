package com.example.client.application;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretkey;

    @Value("${jwt.expiration}")
    private long expiration;

    public String createToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(now.getTime()+ expiration))
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, secretkey)
                .compact();
    }

    public String getEmailByToken(String token) { //토큰추출
       return Jwts.parserBuilder()
               .setSigningKey(secretkey)
               .build()
               .parseClaimsJws(token)
               .getBody()
               .getSubject();
    }

    public boolean validateToken(String token) {
         try{
             Jwts.parserBuilder()
                     .setSigningKey(secretkey)  //사용했던 시크릿키
                     .build()
                     .parseClaimsJws(token); //서명검증
                     return true;
         } catch (Exception e) {
             System.out.println("토큰 검증실패:" +e);
             return false;
         }
    }

    public long getExpiration() {
        return expiration;
    }


}
