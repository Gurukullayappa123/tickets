package com.Kafka.Project.kafka;


import com.Kafka.Project.payload.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaJsonConsumer {

   @KafkaListener(topics="GuruTopic2",groupId = "My_group")
    public void receivedJson(User user){
       System.out.println("User Object Received");

    }
}
