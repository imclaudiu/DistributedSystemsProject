//package com.example.demo.controllers;
//
//import com.example.demo.kafka.MonitorProducer;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class KafkaController {
//
//    private final MonitorProducer monitorProducer;
//
//    public KafkaController(MonitorProducer monitorProducer) {
//        this.monitorProducer = monitorProducer;
//    }
//
//    @GetMapping("/send")
//    public String sendMessage(@RequestParam String message) {
//        monitorProducer.setAddDeviceTopic(message);
//        return "Message sent successfully!";
//    }
//}