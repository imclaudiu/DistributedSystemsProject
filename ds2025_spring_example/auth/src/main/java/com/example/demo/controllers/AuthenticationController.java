package com.example.demo.controllers;

import com.example.demo.dtos.AuthenticationDTO;
import com.example.demo.dtos.AuthenticationDetailsDTO;
import com.example.demo.dtos.LoginDTO;
import com.example.demo.entities.Authentication;
import com.example.demo.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@Validated

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping("/add")
    public Authentication insertAuth(@RequestBody AuthenticationDetailsDTO authentication){ //FA-LE PE TOATE CU RESPONSEENTITY!!!!!!!!!!!
        return this.authenticationService.insertAuth(authentication);
    }

    @GetMapping("/getAll")
    public List<AuthenticationDTO> getAllAuths()
    {
        return this.authenticationService.getAllAuths();
    }

    @GetMapping("/get/{id}")
    public AuthenticationDetailsDTO getAuth(@PathVariable UUID id){
        return this.authenticationService.getAuth(id);
    }

    @PostMapping("/checkPass")
    public Boolean checkPassword(@RequestBody LoginDTO loginDTO){
        return this.authenticationService.checkPassword(loginDTO);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Authentication> update(@PathVariable UUID id, @RequestBody AuthenticationDetailsDTO authenticationDetailsDTO){
        return this.authenticationService.patchDevice(id, authenticationDetailsDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable UUID id){
        this.authenticationService.delete(id);
    }

}
