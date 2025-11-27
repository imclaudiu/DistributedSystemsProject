package com.example.demo.dtos;

import java.util.UUID;

public class RegisterRequest {
    private String username;
    private String password;
    private String role;
    private String name;
    private String address;
    private Integer age;

    public RegisterRequest(String username, String password, String role, String name, String address, Integer age) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.address = address;
        this.age = age;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
