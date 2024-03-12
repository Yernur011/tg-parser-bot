package com.example.parser.service;


import com.example.parser.model.UsersQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaSenderExample {
    private final KafkaTemplate<String, UsersQuery> kafkaTemplate;
    public void sendMessage(String topicName, UsersQuery usersQuery){
        log.info("message send from telegram");
        kafkaTemplate.send(topicName, usersQuery);
    }
}