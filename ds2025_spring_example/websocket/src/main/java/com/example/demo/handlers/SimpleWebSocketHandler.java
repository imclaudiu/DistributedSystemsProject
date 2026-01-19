package com.example.demo.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class SimpleWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("✅ New WebSocket connection: " + session.getId());

        try {
            // Trimite mesaj de bun venit
            session.sendMessage(new TextMessage("Server: Ma sugiBun venit la WebSocket!"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        System.out.println("📩 Received: " + payload + " from " + session.getId());

        // Răspunde automat
        String response = "Server: Ai trimis - " + payload;

        // Trimite la toți clienții
        broadcast(response);

        // Sau trimite doar înapoi la cel care a trimis
        // sendToSession(session, response);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        System.out.println("🔌 WebSocket disconnected: " + session.getId());
    }

    private void broadcast(String message) {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendToSession(WebSocketSession session, String message) {
        if (session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Metodă pentru a trimite mesaje din altă parte a aplicației
    public void sendMessageToAll(String message) {
        broadcast(message);
    }
}