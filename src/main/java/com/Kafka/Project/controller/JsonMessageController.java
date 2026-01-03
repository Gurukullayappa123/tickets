package com.Kafka.Project.controller;


import com.Kafka.Project.kafka.KafkaJsonProducer;
import com.Kafka.Project.payload.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path=("/api/v1/json"))
public class JsonMessageController {

    private KafkaJsonProducer kafkaJsonProducer;

    public JsonMessageController(KafkaJsonProducer kafkaJsonProducer) {
        this.kafkaJsonProducer = kafkaJsonProducer;
    }


    @PostMapping("/publish")
    public ResponseEntity<String>  sendUser(@RequestBody User user)
    {
        kafkaJsonProducer.sendMessages(user);
        System.out.println(" object sent to topic");

        return ResponseEntity.ok("object sent to topic");
    }
}
