package com.example.demo.dtos;

import java.util.UUID;

public class AuthenticationDTO {
    private UUID id;
    private String username;
    private String role;

    public AuthenticationDTO(){

    }

    public AuthenticationDTO(UUID id, String username, String role) {
        this.id = id;
        this.username = username;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
