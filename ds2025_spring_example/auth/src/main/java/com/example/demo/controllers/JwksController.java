package com.example.demo.controllers;

import com.example.demo.config.JwtConfig;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@RestController
public class JwksController {

    @Autowired
    private JwtConfig jwtConfig;

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> keys() throws Exception {
        RSAPublicKey publicKey = (RSAPublicKey) jwtConfig.rsaKeyPair().getPublic();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .keyID("auth-service-key-2025")
                .build();
        return new JWKSet(rsaKey).toJSONObject();
    }
}
