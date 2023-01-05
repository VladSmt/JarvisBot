package com.tgbot.jarvis.jarvisbot.bot;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.tgbot.jarvis.jarvisbot.services.Gpt3Helper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vlad
 */
@Component
public class JarvisBot extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private final Gpt3Helper gpt3Helper;


    public JarvisBot(Gpt3Helper gpt3Helper) {
        this.gpt3Helper = gpt3Helper;
    }

    @SneakyThrows
    public void onUpdateReceived(Update update) {
        sendMsg(update);
    }

    public void sendMsg(Update update) throws UnirestException {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String textFromUser = update.getMessage().getText();

            Long userId = update.getMessage().getChatId();
            String userFirstName = update.getMessage().getFrom().getFirstName();

            if (textFromUser.equals("/start")) {
                try {
                    SendMessage sendMessage = SendMessage.builder()
                            .chatId(userId.toString())
                            .text("Хай, бро! Я Джарвіс, до твоїх послуг :)")
                            .build();
                    this.sendApiMethod(sendMessage);
                } catch (TelegramApiException e) {
//                log.error("Exception when sending message: ", e);
                }
            } else {
                SendMessage sendMessage = SendMessage.builder()
                        .chatId(userId.toString())
                        .text(Gpt3Helper.generateResponse(textFromUser))
                        .build();
                try {
                    this.sendApiMethod(sendMessage);
                } catch (TelegramApiException e) {
//                log.error("Exception when sending message: ", e);
                }
            }

        }
    }


    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
