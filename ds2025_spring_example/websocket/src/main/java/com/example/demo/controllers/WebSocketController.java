package com.example.demo.controllers;

import com.example.demo.handlers.SimpleWebSocketHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api")
@Validated
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class WebSocketController {

    @Autowired
    private SimpleWebSocketHandler webSocketHandler;

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) {
        webSocketHandler.sendMessageToAll("Broadcast: " + message);
        return "Mesaj trimis: " + message;
    }

    @GetMapping("/test")
    public String test() {
        webSocketHandler.sendMessageToAll("Server: Mesaj de test!");
        return "Test broadcast trimis";
    }
}