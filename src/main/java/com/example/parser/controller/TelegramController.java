package com.example.parser.controller;

import com.example.parser.configuration.telegram.BotConfig;
import com.example.parser.model.Product;
import com.example.parser.model.Query;
import com.example.parser.model.UsersQuery;
import com.example.parser.service.KafkaSenderExample;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramController extends TelegramLongPollingBot {
    private final KafkaSenderExample kafkaSenderExample;

    private final BotConfig botConfig;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (update.hasMessage() && message.hasText()) {
            String messageText = message.getText();
            Long chatId = message.getChatId();
            String newMessageText = messageText.replace(' ', '-');
            String txt = "wait pls";

            var query = Query.builder()
                    .queryCity("alma-ata/")
                    .queryCategory("/elektronika/kompyutery-i-komplektuyuschie/")
                    .queryText("q-" + newMessageText + "/?search%5Border%5D=created_at%3Adesc")
                    .build();
            User from = message.getFrom();
            var usersQuery = UsersQuery.builder()
                    .chatId(message.getChatId())
                    .username(
                            from.getUserName() == null
                                    ? from.getFirstName()
                                    : from.getUserName()
                    )
                    .query(query)
                    .build();

            if (messageText.equals("stop")) txt += " stopped";
            if (messageText.equals("/start")) txt += " started";

            kafkaSenderExample.sendMessage("from_telegram", usersQuery);


            sendMessage(chatId, txt);
        }

    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    public void sendMessage(Long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.info("Exception {}", e.getMessage());
        }
    }

    public void sendMessage(Long chatId, List<String> texts) {
        texts.forEach(text -> sendMessage(chatId, text));
    }

    public void sendProducts(Long chatId, List<Product> products) {
        products.forEach(product -> sendMessage(chatId, product.toString()));
    }


}