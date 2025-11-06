package com.example.demo.dtos;

import java.util.UUID;

public class LoginDTO {
    private UUID id;
    private String username;
    private String password;

    public LoginDTO() {

    }

    public LoginDTO(String username, String password){
        this.username = username;
        this.password = password;
    }

//    public LoginDTO(UUID id, String password){
//        this.id = id;
//        this.password = password;
//    }

    public UUID getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
