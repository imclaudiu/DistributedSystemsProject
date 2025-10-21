package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // APIs usually don't need CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/auth/**").permitAll()  // public endpoints
                        .requestMatchers("/devices/**").authenticated()      // protected endpoints
                        .anyRequest().permitAll()                             // everything else allowed
                );
        return http.build();
    }
}
