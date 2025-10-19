package com.example.demo.dtos;

import java.util.UUID;

public class AuthenticationDetailsDTO {
    private UUID id;
    private String username;
    private String password;
    private String role;

    public AuthenticationDetailsDTO() {
    }

    public AuthenticationDetailsDTO(UUID id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public AuthenticationDetailsDTO(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
