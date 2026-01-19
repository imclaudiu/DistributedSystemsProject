package com.example.demo.kafka;

import com.example.demo.handlers.SimpleWebSocketHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {
    @Component
    public class KafkaConsumer {

        private final SimpleWebSocketHandler handler;

        public KafkaConsumer(SimpleWebSocketHandler handler) {
            this.handler = handler;
        }

        @KafkaListener(topics = "over-limit", groupId = "project-group")
        public void loginNotice(String message) {
            handler.sendToAll(message);
        }
    }
}
