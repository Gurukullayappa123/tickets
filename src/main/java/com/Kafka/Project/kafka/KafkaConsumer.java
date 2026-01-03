package com.Kafka.Project.kafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    public static final Logger LOGGER= LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics="GuruTopic",groupId = "My_group")
    public void Consume(String message){

    LOGGER.info(String.format("message Received from Topic -> %s ",message));
    }
}
