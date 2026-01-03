package com.Kafka.Project.controller;


import com.Kafka.Project.kafka.KafkaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/kafka")
public class MessageController {


    private KafkaProducer kafkaProducer;

    public MessageController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;

    }


    @GetMapping(path="/publish")
    public ResponseEntity<String> sendingMessage(@RequestParam("message") String message){
        kafkaProducer.sendMessage(message);
        return  ResponseEntity.ok("message Send to topic");

    }
}
