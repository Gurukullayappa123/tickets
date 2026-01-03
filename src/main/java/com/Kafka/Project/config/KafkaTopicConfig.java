package com.Kafka.Project.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic newTopicCreation(){
        return TopicBuilder.name("GuruTopic").build();
    }
    @Bean
    public NewTopic newTopicJsonCreation(){
        return TopicBuilder.name("GuruTopic2").build();
    }

}
