package com.example.demo.services;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {
    private final JwtDecoder jwtDecoder;

    public TokenService(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    public UUID extractUserId(String token){
        Jwt jwt = jwtDecoder.decode(token);
        return UUID.fromString(jwt.getClaimAsString("id"));
    }
}
