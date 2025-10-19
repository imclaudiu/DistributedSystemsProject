package com.example.demo.dtos.builders;

import com.example.demo.dtos.AuthenticationDTO;
import com.example.demo.dtos.AuthenticationDetailsDTO;
import com.example.demo.entities.Authentication;

public class AuthenticationBuilder {

    public static AuthenticationDetailsDTO toAuthenticationDetailsDTO(Authentication authentication){
        return new AuthenticationDetailsDTO(authentication.getId(), authentication.getUsername(), authentication.getPassword(), authentication.getRole());
    }

    public static AuthenticationDTO toAuthenticationDTO(Authentication authentication){
        return new AuthenticationDTO(authentication.getId(), authentication.getUsername(), authentication.getRole());
    }

    public static Authentication toEntity(AuthenticationDetailsDTO authenticationDetailsDTO){
        return new Authentication(authenticationDetailsDTO.getUsername(), authenticationDetailsDTO.getPassword(), authenticationDetailsDTO.getRole());
    }
}
