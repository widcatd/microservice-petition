package com.petition.jwt;

import com.petition.model.auth.IJwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class JwtProvider implements IJwtProvider {

    @Value("${jwt.secret}")
    private String secret;
//    @Value("${jwt.expiration}")
//    private Integer expiration;

//    @Override
//    public String generateToken(User user) {
//        return Jwts.builder()
//                .subject(user.getEmail())
//                .claim("roles", user.getRole())
//                .issuedAt(new Date())
//                .expiration(new Date(new Date().getTime() + expiration))
//                .signWith(getKey(secret), Jwts.SIG.HS256)
//                .compact();
//    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

//    public String getSubject(String token) {
//        return Jwts.parser()
//                .verifyWith(getKey(secret))
//                .build()
//                .parseSignedClaims(token)
//                .getPayload()
//                .getSubject();
//    }
    @Override
    public Mono<String> getSubject() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName);
    }

    private SecretKey getKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

