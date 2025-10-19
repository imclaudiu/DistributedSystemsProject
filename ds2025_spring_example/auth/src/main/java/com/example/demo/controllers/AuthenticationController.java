package com.example.demo.controllers;

import com.example.demo.dtos.AuthenticationDTO;
import com.example.demo.dtos.AuthenticationDetailsDTO;
import com.example.demo.dtos.LoginDTO;
import com.example.demo.entities.Authentication;
import com.example.demo.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<Void> insertAuth(@Valid @RequestBody AuthenticationDetailsDTO authenticationDetailsDTO){
        Authentication authentication = this.authenticationService.insertAuth(authenticationDetailsDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{/id}")
                .buildAndExpand(authentication.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AuthenticationDTO>> getAllAuths()
    {
        return ResponseEntity.ok(authenticationService.getAllAuths());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AuthenticationDetailsDTO> getAuth(@PathVariable UUID id){
        return ResponseEntity.ok(authenticationService.getAuth(id));
    }

    @PostMapping("/checkPass")
    public ResponseEntity<Boolean> checkPassword(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(authenticationService.checkPassword(loginDTO));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Authentication> update(@PathVariable UUID id, @RequestBody AuthenticationDetailsDTO authenticationDetailsDTO){
        return ResponseEntity.ok(this.authenticationService.patchDevice(id, authenticationDetailsDTO));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable UUID id){
        this.authenticationService.delete(id);
    }

}
