package com.example.demo.controllers;

import com.example.demo.dtos.AuthenticationDTO;
import com.example.demo.dtos.AuthenticationDetailsDTO;
import com.example.demo.dtos.LoginDTO;
import com.example.demo.entities.Authentication;
import com.example.demo.services.AuthenticationService;
import com.example.demo.services.KafkaProducerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@Validated
@CrossOrigin

public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final KafkaProducerService kafkaProducerService;

    public AuthenticationController(AuthenticationService authenticationService, KafkaProducerService kafkaProducerService){
        this.authenticationService = authenticationService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> insertAuth(@Valid @RequestBody AuthenticationDetailsDTO authenticationDetailsDTO) {
        Authentication authentication = this.authenticationService.insertAuth(authenticationDetailsDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(authentication.getId()).toUri();

        // Return both location header and UUID in body
        Map<String, String> response = new HashMap<>();
        response.put("id", authentication.getId().toString());
        response.put("message", "User created successfully");
        kafkaProducerService.sendMessage(authentication.getId().toString());

        return ResponseEntity.created(location)
                .body(response);
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


    @DeleteMapping("/delete/{username}")
    public ResponseEntity<Void> deleteAuth(@PathVariable String username) {
        authenticationService.delete(username);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO) {
        String token = authenticationService.login(loginDTO);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @DeleteMapping("deleteAll")
    public void deleteAll(){
        authenticationService.deleteAll();
    }
}
