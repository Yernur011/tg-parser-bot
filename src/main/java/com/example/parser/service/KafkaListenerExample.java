package com.example.parser.service;


import com.example.parser.controller.TelegramController;
import com.example.parser.model.UsersProducts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@Configuration
public class KafkaListenerExample {

    @Autowired
    TelegramController telegramController;

    @KafkaListener(topics = "to_telegram", groupId = "myGroup")
    void listener(UsersProducts data) {
        log.info("message is {}", data);
        telegramController.sendProducts(data.getChatId(), data.getProductList());
    }
}
